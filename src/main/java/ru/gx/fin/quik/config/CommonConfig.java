package ru.gx.fin.quik.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gx.fin.quik.dbadapter.DbAdapter;

import java.util.HashMap;

public abstract class CommonConfig {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Common">
    @Value("${service.name}")
    private String serviceName;

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
    @Bean
    public KafkaIncomeTopicsConfiguration kafkaIncomeTopicsConfiguration() {
        return new KafkaIncomeTopicsConfiguration("kafka-income-config");
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
