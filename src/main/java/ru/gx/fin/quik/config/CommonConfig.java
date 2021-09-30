package ru.gx.fin.quik.config;

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
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gx.fin.quik.dbadapter.DbAdapterLifeController;
import ru.gx.fin.quik.dbadapter.DbAdapterSettingsContainer;
import ru.gx.fin.quik.descriptors.QuikDealsLoadingDescriptor;
import ru.gx.fin.quik.descriptors.QuikOrdersLoadingDescriptor;
import ru.gx.fin.quik.events.LoadedSecuritiesEvent;
import ru.gx.kafka.TopicMessageMode;
import ru.gx.kafka.load.LoadingMode;
import ru.gx.kafka.load.SimpleIncomeTopicsLoader;
import ru.gx.fin.gate.quik.model.memdata.QuikAllTradesMemoryRepository;
import ru.gx.fin.gate.quik.model.memdata.QuikDealsMemoryRepository;
import ru.gx.fin.gate.quik.model.memdata.QuikOrdersMemoryRepository;
import ru.gx.fin.gate.quik.model.memdata.QuikSecuritiesMemoryRepository;
import ru.gx.fin.quik.converters.AllTradeEntityFromDtoConverter;
import ru.gx.fin.quik.converters.DealEntityFromDtoConverter;
import ru.gx.fin.quik.converters.OrderEntityFromDtoConverter;
import ru.gx.fin.quik.converters.SecurityEntityFromDtoConverter;
import ru.gx.fin.quik.dbadapter.DbAdapter;
import ru.gx.fin.quik.descriptors.QuikAllTradesLoadingDescriptor;
import ru.gx.fin.quik.descriptors.QuikSecuritiesLoadingDescriptor;
import ru.gx.fin.quik.events.LoadedAllTradesEvent;
import ru.gx.fin.quik.events.LoadedDealsEvent;
import ru.gx.fin.quik.events.LoadedOrdersEvent;
import ru.gx.settings.SimpleSettingsController;
import ru.gx.worker.SimpleWorker;

import java.util.HashMap;
import java.util.Properties;

@EnableJpaRepositories("ru.gx.fin.quik.repositories")
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
    @Bean
    public SimpleSettingsController simpleSettingsController() {
        return new SimpleSettingsController(this.serviceName);
    }

    @Bean
    public SimpleWorker simpleWorker() {
        return new SimpleWorker(this.serviceName);
    }

    @Bean
    public DbAdapterSettingsContainer dbAdapterSettingsContainer() {
        return new DbAdapterSettingsContainer();
    }

    @Bean
    public DbAdapter dbAdapter() {
        return new DbAdapter();
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
    public QuikAllTradesMemoryRepository quikAllTradesMemoryRepository() {
        return new QuikAllTradesMemoryRepository();
    }

    @SneakyThrows
    @Bean
    public QuikDealsMemoryRepository quikDealsMemoryRepository() {
        return new QuikDealsMemoryRepository();
    }

    @SneakyThrows
    @Bean
    public QuikOrdersMemoryRepository quikOrdersMemoryRepository() {
        return new QuikOrdersMemoryRepository();
    }

    @SneakyThrows
    @Bean
    public QuikSecuritiesMemoryRepository quikSecuritiesMemoryRepository() {
        return new QuikSecuritiesMemoryRepository();
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
    public SimpleIncomeTopicsLoader incomeTopicsLoader(ObjectMapper objectMapper, DbAdapterSettingsContainer settings) {
        final var result = new SimpleIncomeTopicsLoader(objectMapper);
        result.getDescriptorsDefaults()
                .setTopicMessageMode(TopicMessageMode.PACKAGE)
                .setLoadingMode(LoadingMode.Auto)
                .setConsumerProperties(consumerProperties())
                .setPartitions(0);

        result
                .register(
                        new QuikSecuritiesLoadingDescriptor(settings.getIncomeTopicSecurities(), result.getDescriptorsDefaults())
                                .setPriority(0)
                                .setOnLoadedEventClass(LoadedSecuritiesEvent.class)
                )
                .register(
                        new QuikOrdersLoadingDescriptor(settings.getIncomeTopicOrders(), result.getDescriptorsDefaults())
                                .setPriority(1)
                                .setOnLoadedEventClass(LoadedOrdersEvent.class)
                )
                .register(
                        new QuikDealsLoadingDescriptor(settings.getIncomeTopicDeals(), result.getDescriptorsDefaults())
                                .setPriority(2)
                                .setOnLoadedEventClass(LoadedDealsEvent.class)
                )
                .register(
                        new QuikAllTradesLoadingDescriptor(settings.getIncomeTopicAllTrades(), result.getDescriptorsDefaults())
                                .setPriority(3)
                                .setOnLoadedEventClass(LoadedAllTradesEvent.class)
                );

        return result;
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
