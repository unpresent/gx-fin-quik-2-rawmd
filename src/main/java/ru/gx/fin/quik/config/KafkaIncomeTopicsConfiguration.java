package ru.gx.fin.quik.config;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.gx.core.channels.IncomeDataProcessType;
import ru.gx.core.kafka.load.AbstractKafkaIncomeTopicsConfiguration;
import ru.gx.core.kafka.load.KafkaIncomeTopicLoadingDescriptor;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamAllTradesPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamDealsPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamOrdersPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Properties;

import static lombok.AccessLevel.PROTECTED;

@Configuration
public class KafkaIncomeTopicsConfiguration extends AbstractKafkaIncomeTopicsConfiguration {

    @Getter(PROTECTED)
    private final String serviceName;

    @Getter(PROTECTED)
    private final String kafkaServer;

    @Getter(PROTECTED)
    private final QuikProviderStreamAllTradesPackageDataPublishChannelApiV1 allTradesChannelApi;

    @Getter(PROTECTED)
    private final QuikProviderStreamDealsPackageDataPublishChannelApiV1 dealsChannelApi;

    @Getter(PROTECTED)
    private final QuikProviderStreamOrdersPackageDataPublishChannelApiV1 ordersChannelApi;

    @Getter(PROTECTED)
    private final QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1 securitiesChannelApi;

    public KafkaIncomeTopicsConfiguration(
            @NotNull final @Value("${service.name}") String serviceName,
            @NotNull final @Value(value = "${service.kafka.server}") String kafkaServer,
            @NotNull final QuikProviderStreamAllTradesPackageDataPublishChannelApiV1 allTradesChannelApi,
            @NotNull final QuikProviderStreamDealsPackageDataPublishChannelApiV1 dealsChannelApi,
            @NotNull final QuikProviderStreamOrdersPackageDataPublishChannelApiV1 ordersChannelApi,
            @NotNull final QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1 securitiesChannelApi
    ) {
        super("kafka-income-config");
        this.serviceName = serviceName;
        this.kafkaServer = kafkaServer;

        this.allTradesChannelApi = allTradesChannelApi;
        this.dealsChannelApi = dealsChannelApi;
        this.ordersChannelApi = ordersChannelApi;
        this.securitiesChannelApi = securitiesChannelApi;
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        this.getDescriptorsDefaults()
                .setDurationOnPoll(Duration.ofMillis(25))
                .setPartitions(0)
                .setConsumerProperties(consumerProperties());
                // .setProcessType(IncomeDataProcessType.Immediate);

        this
                .newDescriptor(this.ordersChannelApi, KafkaIncomeTopicLoadingDescriptor.class)
                .setPriority(0)
                .init();

        this
                .newDescriptor(this.dealsChannelApi, KafkaIncomeTopicLoadingDescriptor.class)
                .setPriority(1)
                .init();

        this
                .newDescriptor(this.securitiesChannelApi, KafkaIncomeTopicLoadingDescriptor.class)
                .setPriority(2)
                .init();

        this
                .newDescriptor(this.allTradesChannelApi, KafkaIncomeTopicLoadingDescriptor.class)
                .setPriority(3)
                .init();
    }

    public Properties consumerProperties() {
        final var result = new Properties();
        result.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        result.put(ConsumerConfig.GROUP_ID_CONFIG, this.serviceName);
        result.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        result.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        result.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return result;
    }
}
