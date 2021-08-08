package ru.gxfin.quik.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gxfin.common.kafka.TopicMessageMode;
import ru.gxfin.common.kafka.loader.StandardIncomeTopicsLoader;
import ru.gxfin.gate.quik.model.memdata.QuikAllTradesMemoryRepository;
import ru.gxfin.gate.quik.model.memdata.QuikDealsMemoryRepository;
import ru.gxfin.gate.quik.model.memdata.QuikOrdersMemoryRepository;
import ru.gxfin.gate.quik.model.memdata.QuikSecuritiesMemoryRepository;
import ru.gxfin.quik.config.helper.WorkIncomeTopicsConfiguration;
import ru.gxfin.quik.dbadapter.DbAdapter;
import ru.gxfin.quik.dbadapter.DbAdapterLifeController;
import ru.gxfin.quik.dbadapter.DbAdapterSettingsController;
import ru.gxfin.quik.events.LoadedAllTradesEvent;
import ru.gxfin.quik.events.LoadedDealsEvent;
import ru.gxfin.quik.events.LoadedOrdersEvent;
import ru.gxfin.quik.events.LoadedSecuritiesEvent;
import ru.gxfin.quik.services.AllTradesTransformer;
import ru.gxfin.quik.services.DealsTransformer;
import ru.gxfin.quik.services.OrdersTransformer;
import ru.gxfin.quik.services.SeciritiesTransformer;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Properties;

@EnableJpaRepositories("ru.gxfin.quik.repositories")
public class CommonConfig {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Common">
    @Value("${service.name}")
    private String serviceName;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    // @Bean
    // @Autowired
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("entityManagerFactory is not a hibernate factory!");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
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
    // <editor-fold desc="Transformers">
    @Bean
    public AllTradesTransformer allTradesTransformer() {
        return new AllTradesTransformer();
    }

    @Bean
    public DealsTransformer dealsTransformer() {
        return new DealsTransformer();
    }

    @Bean
    public OrdersTransformer ordersTransformer() {
        return new OrdersTransformer();
    }

    @Bean
    public SeciritiesTransformer seciritiesTransformer() {
        return new SeciritiesTransformer();
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
    public StandardIncomeTopicsLoader incomeTopicsLoader(ApplicationContext context) {
        return new StandardIncomeTopicsLoader(context);
    }

    @Bean
    @Autowired
    public WorkIncomeTopicsConfiguration workIncomeTopicsConfiguration(
            ApplicationContext context,
            DbAdapterSettingsController settings,

            QuikAllTradesMemoryRepository quikAllTradesMemoryRepository,
            QuikDealsMemoryRepository quikDealsMemoryRepository,
            QuikOrdersMemoryRepository quikOrdersMemoryRepository,
            QuikSecuritiesMemoryRepository quikSecuritiesMemoryRepository
    ) {
        final var props = consumerProperties();
        final var result = new WorkIncomeTopicsConfiguration(context)
                .register(0, settings.getIncomeTopicSecurities(), quikSecuritiesMemoryRepository, TopicMessageMode.PACKAGE, LoadedSecuritiesEvent.class, props, 0)
                .register(1, settings.getIncomeTopicOrders(), quikOrdersMemoryRepository, TopicMessageMode.PACKAGE, LoadedOrdersEvent.class, props, 0)
                .register(2, settings.getIncomeTopicDeals(), quikDealsMemoryRepository, TopicMessageMode.PACKAGE, LoadedDealsEvent.class, props, 0)
                .register(3, settings.getIncomeTopicAlltrades(), quikAllTradesMemoryRepository, TopicMessageMode.PACKAGE, LoadedAllTradesEvent.class, props, 0);

        return (WorkIncomeTopicsConfiguration) result;
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
