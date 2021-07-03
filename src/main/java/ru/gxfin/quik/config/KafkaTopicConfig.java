package ru.gxfin.quik.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gxfin.common.data.DataService;
import ru.gxfin.quik.api.DbAdapterSettingsController;
import ru.gxfin.quik.services.AllTradesService;
import ru.gxfin.quik.services.DealsService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${kafka.server}")
    private String kafkaServer;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        return new KafkaAdmin(configs);
    }
}
