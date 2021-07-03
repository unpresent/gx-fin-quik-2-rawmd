package ru.gxfin.quik.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gxfin.quik.api.DbAdapterSettingsController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.group.id}")
    private String kafkaGroupId;

    @Bean
    public Properties consumerProperties() {
        final var props  = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    @Autowired
    public Consumer<Long, String> consumer(List<TopicPartition> topicPartitions) {
        final Consumer<Long, String> result = new KafkaConsumer<>(consumerProperties());
        result.assign(topicPartitions);
        result.seekToBeginning(topicPartitions);
        return result;
    }

    @Bean
    @Autowired
    public List<TopicPartition> topicPartitions(DbAdapterSettingsController settings) {
        final List<TopicPartition> result = new ArrayList<>();
        result.add(new TopicPartition(settings.getIncomeTopicAlltrades(), 0));
        result.add(new TopicPartition(settings.getIncomeTopicDeals(), 0));
        result.add(new TopicPartition(settings.getIncomeTopicOrders(), 0));
        return result;
    }
}
