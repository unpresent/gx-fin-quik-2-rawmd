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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gx.core.channels.ChannelMessageMode;
import ru.gx.core.channels.ChannelsConfiguration;
import ru.gx.core.channels.ChannelsConfigurator;
import ru.gx.core.channels.SerializeMode;
import ru.gx.core.kafka.load.AbstractKafkaIncomeTopicsConfiguration;
import ru.gx.core.kafka.load.KafkaIncomeTopicLoadingDescriptor;
import ru.gx.fin.quik.dbadapter.DbAdapter;
import ru.gx.fin.quik.dbadapter.DbAdapterSettingsContainer;
import ru.gx.fin.quik.events.LoadedAllTradesEvent;
import ru.gx.fin.quik.events.LoadedDealsEvent;
import ru.gx.fin.quik.events.LoadedOrdersEvent;
import ru.gx.fin.quik.events.LoadedSecuritiesEvent;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import static lombok.AccessLevel.PROTECTED;

@EnableConfigurationProperties(ConfigurationPropertiesServiceKafka.class)
public abstract class CommonConfig implements ChannelsConfigurator {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Common">
    @Value("${service.name}")
    private String serviceName;

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
        return new DbAdapter(serviceName);
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
    @Value(value = "${service.kafka.server}")
    private String kafkaServer;

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
        result.put(ConsumerConfig.GROUP_ID_CONFIG, this.serviceName);
        result.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        result.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        result.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configureChannels(@NotNull ChannelsConfiguration channelsConfiguration) {
        if (channelsConfiguration instanceof final AbstractKafkaIncomeTopicsConfiguration config) {
            config.getDescriptorsDefaults()
                    .setDurationOnPoll(Duration.ofMillis(25))
                    .setPartitions(0)
                    .setConsumerProperties(consumerProperties())
                    .setSerializeMode(SerializeMode.JsonString)
                    .setMessageMode(ChannelMessageMode.Package);

            config
                    .newDescriptor(this.settings.getIncomeTopicSecurities(), KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedSecuritiesEvent.class)
                    .setPriority(0)
                    .init();

            config
                    .newDescriptor(this.settings.getIncomeTopicOrders(), KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedOrdersEvent.class)
                    .setPriority(0)
                    .init();

            config
                    .newDescriptor(this.settings.getIncomeTopicDeals(), KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedDealsEvent.class)
                    .setPriority(0)
                    .init();

            config
                    .newDescriptor(this.settings.getIncomeTopicAllTrades(), KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedAllTradesEvent.class)
                    .setPriority(1)
                    .init();
        }
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
