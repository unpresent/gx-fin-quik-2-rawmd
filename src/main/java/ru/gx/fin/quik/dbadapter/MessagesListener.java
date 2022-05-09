package ru.gx.fin.quik.dbadapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gx.core.channels.ChannelDirection;
import ru.gx.core.data.save.DbSavingDescriptor;
import ru.gx.core.data.sqlwrapping.ThreadConnectionsWrapper;
import ru.gx.core.jdbc.sqlwrapping.JdbcConnectionWrapper;
import ru.gx.core.kafka.KafkaConstants;
import ru.gx.core.kafka.offsets.TopicPartitionOffset;
import ru.gx.core.kafka.offsets.TopicsOffsetsStorage;
import ru.gx.core.messaging.Message;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamAllTradesPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamDealsPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamOrdersPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.messages.QuikProviderStreamAllTradesPackageDataPublish;
import ru.gx.fin.gate.quik.provider.messages.QuikProviderStreamDealsPackageDataPublish;
import ru.gx.fin.gate.quik.provider.messages.QuikProviderStreamOrdersPackageDataPublish;
import ru.gx.fin.gate.quik.provider.messages.QuikProviderStreamSecuritiesPackageDataPublish;
import ru.gx.fin.quik.config.DbSavingConfig;

import javax.activation.UnsupportedDataTypeException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

@Slf4j
@Component
public class MessagesListener {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @NotNull
    private final String serviceName;

    @NotNull
    private DataSource dataSource;

    @NotNull
    private final ThreadConnectionsWrapper connectionsWrapper;

    @NotNull
    private final TopicsOffsetsStorage topicsOffsetsStorage;

    @NotNull
    private final DbSavingConfig dbSavingConfig;

    @NotNull
    private final QuikProviderStreamAllTradesPackageDataPublishChannelApiV1 allTradesChannelApi;

    @NotNull
    private final QuikProviderStreamDealsPackageDataPublishChannelApiV1 dealsChannelApi;

    @NotNull
    private final QuikProviderStreamOrdersPackageDataPublishChannelApiV1 ordersChannelApi;

    @NotNull
    private final QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1 securitiesChannelApi;

    private DbSavingDescriptor<?> securitiesSavingDescriptor;

    private DbSavingDescriptor<?> allTradesSavingDescriptor;

    private DbSavingDescriptor<?> dealsSavingDescriptor;

    private DbSavingDescriptor<?> ordersSavingDescriptor;

    public MessagesListener(
            @NotNull @Value("${service.name}") final String serviceName,
            @NotNull final DataSource dataSource,
            @NotNull final ThreadConnectionsWrapper connectionsWrapper,
            @NotNull final TopicsOffsetsStorage topicsOffsetsStorage,
            @NotNull final DbSavingConfig dbSavingConfig,
            @NotNull final QuikProviderStreamAllTradesPackageDataPublishChannelApiV1 allTradesChannelApi,
            @NotNull final QuikProviderStreamDealsPackageDataPublishChannelApiV1 dealsChannelApi,
            @NotNull final QuikProviderStreamOrdersPackageDataPublishChannelApiV1 ordersChannelApi,
            @NotNull final QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1 securitiesChannelApi
    ) {
        this.serviceName = serviceName;
        this.dataSource = dataSource;
        this.connectionsWrapper = connectionsWrapper;
        this.topicsOffsetsStorage = topicsOffsetsStorage;
        this.dbSavingConfig = dbSavingConfig;
        this.allTradesChannelApi = allTradesChannelApi;
        this.dealsChannelApi = dealsChannelApi;
        this.ordersChannelApi = ordersChannelApi;
        this.securitiesChannelApi = securitiesChannelApi;
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Initialization">
    @PostConstruct
    protected void init() {
        this.allTradesSavingDescriptor = (DbSavingDescriptor<?>)
                requireNonNull(this.dbSavingConfig).get(this.allTradesChannelApi.getName());

        this.dealsSavingDescriptor = (DbSavingDescriptor<?>)
                requireNonNull(this.dbSavingConfig).get(this.dealsChannelApi.getName());

        this.ordersSavingDescriptor = (DbSavingDescriptor<?>)
                requireNonNull(this.dbSavingConfig).get(this.ordersChannelApi.getName());

        this.securitiesSavingDescriptor = (DbSavingDescriptor<?>)
                requireNonNull(this.dbSavingConfig).get(this.securitiesChannelApi.getName());
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий о чтении данных из Kafka">
    private void saveOffsets(
            @NotNull final Message<?> message
    ) {
        final var topicName = message.getChannelDescriptor().getApi().getName();

        final var partition = message.getMetadataValue(KafkaConstants.METADATA_PARTITION);
        final var offset = message.getMetadataValue(KafkaConstants.METADATA_OFFSET);
        if (partition instanceof Integer && offset instanceof Long) {
            final var tps = new TopicPartitionOffset(
                    topicName,
                    (Integer) partition,
                    (Long) offset
            );

            final var tpsArray = new ArrayList<TopicPartitionOffset>();
            tpsArray.add(tps);

            this.topicsOffsetsStorage
                    .saveOffsets(ChannelDirection.In, this.serviceName, tpsArray);
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.provider.out.QuikSecurity}.
     *
     * @param message Объект-событие с параметрами.
     */
    @SneakyThrows(
            {SQLException.class, JsonProcessingException.class, UnsupportedDataTypeException.class, IOException.class}
    )
    @EventListener(QuikProviderStreamSecuritiesPackageDataPublish.class)
    public void loadedSecurities(QuikProviderStreamSecuritiesPackageDataPublish message) {
        log.debug("Starting loadedSecurities()");
        try {
            try (final var ignored = connectionsWrapper.getCurrentThreadConnection()) {
                this.securitiesSavingDescriptor.processPackage(message.getBody().getData());
                saveOffsets(message);
            }
        } finally {
            log.debug("Finished loadedSecurities()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.provider.out.QuikDeal}.
     *
     * @param message Объект-событие с параметрами.
     */
    @SneakyThrows(
            {SQLException.class, JsonProcessingException.class, UnsupportedDataTypeException.class, IOException.class}
    )
    @EventListener(QuikProviderStreamDealsPackageDataPublish.class)
    public void loadedDeals(QuikProviderStreamDealsPackageDataPublish message) {
        log.debug("Starting loadedDeals()");
        try {
            try (final var ignored = connectionsWrapper.getCurrentThreadConnection()) {
                this.dealsSavingDescriptor.processPackage(message.getBody().getData());
                saveOffsets(message);
            }
        } finally {
            log.debug("Finished loadedDeals()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.provider.out.QuikOrder}.
     *
     * @param message Объект-событие с параметрами.
     */
    @SneakyThrows(
            {SQLException.class, JsonProcessingException.class, UnsupportedDataTypeException.class, IOException.class}
    )
    @EventListener(QuikProviderStreamOrdersPackageDataPublish.class)
    public void loadedOrders(QuikProviderStreamOrdersPackageDataPublish message) {
        log.debug("Starting loadedOrders()");
        try {
            try (final var ignored = connectionsWrapper.getCurrentThreadConnection()) {
                this.ordersSavingDescriptor.processPackage(message.getBody().getData());
                saveOffsets(message);
            }
        } finally {
            log.debug("Finished loadedOrders()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.provider.out.QuikAllTrade}.
     *
     * @param message Объект-событие с параметрами.
     */
    @SneakyThrows(
            {SQLException.class, JsonProcessingException.class, UnsupportedDataTypeException.class, IOException.class}
    )
    @EventListener(QuikProviderStreamAllTradesPackageDataPublish.class)
    public void loadedAllTrades(QuikProviderStreamAllTradesPackageDataPublish message) {
        log.debug("Starting loadedAllTrades()");
        try {
            try (final var ignored = connectionsWrapper.getCurrentThreadConnection()) {
                this.allTradesSavingDescriptor.processPackage(message.getBody().getData());
                saveOffsets(message);
            }
        } finally {
            log.debug("Finished loadedAllTrades()");
        }
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
