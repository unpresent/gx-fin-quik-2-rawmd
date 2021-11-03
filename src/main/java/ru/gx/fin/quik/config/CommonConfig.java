package ru.gx.fin.quik.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Setter;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gx.fin.quik.dbadapter.DbAdapter;
import ru.gx.fin.quik.dbadapter.DbAdapterSettingsContainer;
import ru.gx.fin.quik.events.LoadedAllTradesEvent;
import ru.gx.fin.quik.events.LoadedDealsEvent;
import ru.gx.fin.quik.events.LoadedOrdersEvent;
import ru.gx.fin.quik.events.LoadedSecuritiesEvent;
import ru.gx.kafka.SerializeMode;
import ru.gx.kafka.TopicMessageMode;
import ru.gx.kafka.load.IncomeTopicsConfiguration;
import ru.gx.kafka.load.IncomeTopicsConfigurator;
import ru.gx.kafka.load.LoadingMode;
import ru.gx.kafka.load.RawDataIncomeTopicLoadingDescriptor;
import ru.gx.kafka.offsets.TopicsOffsetsLoader;
import ru.gx.kafka.offsets.TopicsOffsetsSaver;
import ru.gx.std.offsets.JdbcTopicsOffsetsLoader;
import ru.gx.std.offsets.JdbcTopicsOffsetsSaver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import static lombok.AccessLevel.PROTECTED;

@EnableConfigurationProperties(ConfigurationPropertiesKafka.class)
public abstract class CommonConfig implements IncomeTopicsConfigurator {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Common">
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DbAdapterSettingsContainer settings;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="DbAdapter & Settings">
    @Bean
    public DbAdapterSettingsContainer dbAdapterSettingsContainer() {
        return new DbAdapterSettingsContainer();
    }

    @Bean
    public DbAdapter dbAdapter() {
        return new DbAdapter();
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

    // TODO: Подумать...
    @Value("${service.name}")
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
        final var result = new Properties();
        result.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        result.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        result.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        result.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        result.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return result;
    }

    @Override
    public void configureIncomeTopics(@NotNull IncomeTopicsConfiguration incomeTopicsConfiguration) {
        incomeTopicsConfiguration.getDescriptorsDefaults()
                .setSerializeMode(SerializeMode.String)
                .setTopicMessageMode(TopicMessageMode.Package)
                .setLoadingMode(LoadingMode.Auto)
                .setDurationOnPoll(Duration.ofMillis(10))
                .setPartitions(0)
                .setConsumerProperties(consumerProperties());

        incomeTopicsConfiguration
                .newDescriptor(this.settings.getIncomeTopicSecurities(), RawDataIncomeTopicLoadingDescriptor.class)
                .setPriority(0)
                .setOnRawDataLoadedEventClass(LoadedSecuritiesEvent.class)
                .init();

        incomeTopicsConfiguration
                .newDescriptor(this.settings.getIncomeTopicOrders(), RawDataIncomeTopicLoadingDescriptor.class)
                .setPriority(1)
                .setOnRawDataLoadedEventClass(LoadedOrdersEvent.class)
                .init();

        incomeTopicsConfiguration
                .newDescriptor(this.settings.getIncomeTopicDeals(), RawDataIncomeTopicLoadingDescriptor.class)
                .setPriority(2)
                .setOnRawDataLoadedEventClass(LoadedDealsEvent.class)
                .init();

        incomeTopicsConfiguration
                .newDescriptor(this.settings.getIncomeTopicAllTrades(), RawDataIncomeTopicLoadingDescriptor.class)
                .setPriority(3)
                .setOnRawDataLoadedEventClass(LoadedAllTradesEvent.class)
                .init();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
