package ru.gxfin.quik.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gxfin.common.kafka.TopicMessageMode;
import ru.gxfin.common.kafka.loader.LoadingMode;
import ru.gxfin.common.kafka.loader.StandardIncomeTopicsLoader;
import ru.gxfin.gate.quik.model.memdata.QuikAllTradesMemoryRepository;
import ru.gxfin.gate.quik.model.memdata.QuikDealsMemoryRepository;
import ru.gxfin.gate.quik.model.memdata.QuikOrdersMemoryRepository;
import ru.gxfin.gate.quik.model.memdata.QuikSecuritiesMemoryRepository;
import ru.gxfin.quik.config.helper.WorkIncomeTopicsConfiguration;
import ru.gxfin.quik.converters.*;
import ru.gxfin.quik.dbadapter.DbAdapter;
import ru.gxfin.quik.dbadapter.DbAdapterLifeController;
import ru.gxfin.quik.dbadapter.DbAdapterSettingsController;
import ru.gxfin.quik.events.LoadedAllTradesEvent;
import ru.gxfin.quik.events.LoadedDealsEvent;
import ru.gxfin.quik.events.LoadedOrdersEvent;
import ru.gxfin.quik.events.LoadedSecuritiesEvent;
import ru.gxfin.quik.loading.QuikAllTradesLoadingDescriptor;
import ru.gxfin.quik.loading.QuikDealsLoadingDescriptor;
import ru.gxfin.quik.loading.QuikOrdersLoadingDescriptor;
import ru.gxfin.quik.loading.QuikSecuritiesLoadingDescriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@EnableJpaRepositories("ru.gxfin.quik.repositories")
public abstract class CommonConfig {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Common">
    @Value("${service.name}")
    private String serviceName;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="DbAdapter & Settings">
    @SneakyThrows
    @Bean
    @Autowired
    public DbAdapterSettingsController dbAdapterSettingsController(ApplicationContext context) {
        return new DbAdapterSettingsController(context);
    }

    @Bean
    public DbAdapter dbAdapter() {
        return new DbAdapter(this.serviceName);
    }

    @Bean
    public DbAdapterLifeController dbAdapterLifeController() {
        return new DbAdapterLifeController();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Converters">
    @Bean
    public AllTradeEntityFromDtoConverter allTradesEntityFromDtoConverter() {
        return new AllTradeEntityFromDtoConverter();
    }

    @Bean
    public DealEntityFromDtoConverter dealsEntityFromDtoConverter() {
        return new DealEntityFromDtoConverter();
    }

    @Bean
    public OrderEntityFromDtoConverter ordersEntityFromDtoConverter() {
        return new OrderEntityFromDtoConverter();
    }

    @Bean
    public SecurityEntityFromDtoConverter securitiesEntityFromDtoConverter() {
        return new SecurityEntityFromDtoConverter();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="MemoryRepositories">
    @SneakyThrows
    @Bean
    @Autowired
    public QuikAllTradesMemoryRepository quikAllTradesMemoryRepository(ObjectMapper objectMapper) {
        return new QuikAllTradesMemoryRepository(objectMapper);
    }

    @SneakyThrows
    @Bean
    @Autowired
    public QuikDealsMemoryRepository quikDealsMemoryRepository(ObjectMapper objectMapper) {
        return new QuikDealsMemoryRepository(objectMapper);
    }

    @SneakyThrows
    @Bean
    @Autowired
    public QuikOrdersMemoryRepository quikOrdersMemoryRepository(ObjectMapper objectMapper) {
        return new QuikOrdersMemoryRepository(objectMapper);
    }

    @SneakyThrows
    @Bean
    @Autowired
    public QuikSecuritiesMemoryRepository quikSecuritiesMemoryRepository(ObjectMapper objectMapper) {
        return new QuikSecuritiesMemoryRepository(objectMapper);
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Events">
    @Bean
    @Autowired
    public LoadedAllTradesEvent loadedAllTradesEvent(DbAdapter dbAdapter) {
        return new LoadedAllTradesEvent(dbAdapter);
    }

    @Bean
    @Autowired
    public LoadedOrdersEvent loadedOrdersEvent(DbAdapter dbAdapter) {
        return new LoadedOrdersEvent(dbAdapter);
    }

    @Bean
    @Autowired
    public LoadedDealsEvent loadedDealsEvent(DbAdapter dbAdapter) {
        return new LoadedDealsEvent(dbAdapter);
    }

    @Bean
    @Autowired
    public LoadedSecuritiesEvent loadedSecuritiesEvent(DbAdapter dbAdapter) {
        return new LoadedSecuritiesEvent(dbAdapter);
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Kafka Common">
    @Value(value = "${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.group_id}")
    private String kafkaGroupId;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        return new KafkaAdmin(configs);
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Kafka Consumers">
    public Properties consumerProperties() {
        final var props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    @Autowired
    public StandardIncomeTopicsLoader incomeTopicsLoader(ApplicationContext context, ObjectMapper objectMapper) {
        return new StandardIncomeTopicsLoader(context, objectMapper);
    }

    @Bean
    @Autowired
    public WorkIncomeTopicsConfiguration workIncomeTopicsConfiguration(
            DbAdapterSettingsController settings
    ) {
        final var result = new WorkIncomeTopicsConfiguration();
        result.getDescriptorsDefaults()
                .setTopicMessageMode(TopicMessageMode.PACKAGE)
                .setLoadingMode(LoadingMode.Auto)
                .setConsumerProperties(consumerProperties())
                .setPartitions(0);

        result
                .register(
                        new QuikSecuritiesLoadingDescriptor(result, settings.getIncomeTopicSecurities())
                                .setPriority(0)
                                .setOnLoadedEventClass(LoadedSecuritiesEvent.class)
                )
                .register(
                        new QuikOrdersLoadingDescriptor(result, settings.getIncomeTopicOrders())
                                .setPriority(1)
                                .setOnLoadedEventClass(LoadedOrdersEvent.class)
                )
                .register(
                        new QuikDealsLoadingDescriptor(result, settings.getIncomeTopicDeals())
                                .setPriority(2)
                                .setOnLoadedEventClass(LoadedDealsEvent.class)
                )
                .register(
                        new QuikAllTradesLoadingDescriptor(result, settings.getIncomeTopicAlltrades())
                                .setPriority(3)
                                .setOnLoadedEventClass(LoadedAllTradesEvent.class)
                );

        return result;
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
