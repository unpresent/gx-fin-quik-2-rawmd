package ru.gx.fin.quik.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gx.core.channels.ChannelMessageMode;
import ru.gx.core.channels.ChannelsConfiguration;
import ru.gx.core.channels.ChannelsConfigurator;
import ru.gx.core.channels.SerializeMode;
import ru.gx.core.kafka.load.AbstractKafkaIncomeTopicsConfiguration;
import ru.gx.core.kafka.load.KafkaIncomeTopicLoadingDescriptor;
import ru.gx.fin.gate.quik.provider.config.QuikProviderChannelsNames;
import ru.gx.fin.gate.quik.provider.events.LoadedQuikProviderStreamAllTradesEvent;
import ru.gx.fin.gate.quik.provider.events.LoadedQuikProviderStreamDealsEvent;
import ru.gx.fin.gate.quik.provider.events.LoadedQuikProviderStreamOrdersEvent;
import ru.gx.fin.gate.quik.provider.events.LoadedQuikProviderStreamSecuritiesEvent;
import ru.gx.fin.quik.dbadapter.DbAdapter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

public abstract class CommonConfig implements ChannelsConfigurator {
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
    @Bean
    public DbAdapter dbAdapter() {
        return new DbAdapter(serviceName);
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
                    .newDescriptor(QuikProviderChannelsNames.Streams.SECURITIES, KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedQuikProviderStreamSecuritiesEvent.class)
                    .setPriority(0)
                    .init();

            config
                    .newDescriptor(QuikProviderChannelsNames.Streams.ORDERS, KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedQuikProviderStreamOrdersEvent.class)
                    .setPriority(1)
                    .init();

            config
                    .newDescriptor(QuikProviderChannelsNames.Streams.DEALS, KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedQuikProviderStreamDealsEvent.class)
                    .setPriority(2)
                    .init();

            config
                    .newDescriptor(QuikProviderChannelsNames.Streams.ALL_TRADES, KafkaIncomeTopicLoadingDescriptor.class)
                    .setDataLoadedEventClass(LoadedQuikProviderStreamAllTradesEvent.class)
                    .setPriority(3)
                    .init();
        }
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
