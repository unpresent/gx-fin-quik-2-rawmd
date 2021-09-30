package ru.gx.fin.quik.dbadapter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import ru.gx.data.InvalidDataObjectTypeException;
import ru.gx.fin.quik.converters.AllTradeEntityFromDtoConverter;
import ru.gx.fin.quik.converters.DealEntityFromDtoConverter;
import ru.gx.fin.quik.converters.OrderEntityFromDtoConverter;
import ru.gx.fin.quik.converters.SecurityEntityFromDtoConverter;
import ru.gx.fin.quik.entities.AllTradeEntitiesPackage;
import ru.gx.fin.quik.entities.DealEntitiesPackage;
import ru.gx.fin.quik.entities.OrderEntitiesPackage;
import ru.gx.fin.quik.entities.SecurityEntitiesPackage;
import ru.gx.fin.quik.entities.kafka.KafkaIncomeOffsetEntity;
import ru.gx.fin.quik.events.LoadedAllTradesEvent;
import ru.gx.fin.quik.events.LoadedDealsEvent;
import ru.gx.fin.quik.events.LoadedOrdersEvent;
import ru.gx.fin.quik.events.LoadedSecuritiesEvent;
import ru.gx.fin.quik.repositories.AllTradesRepository;
import ru.gx.fin.quik.repositories.DealsRepository;
import ru.gx.fin.quik.repositories.OrdersRepository;
import ru.gx.fin.quik.repositories.SecuritiesRepository;
import ru.gx.fin.quik.repositories.kafka.KafkaIncomeOffsetsRepository;
import ru.gx.kafka.PartitionOffset;
import ru.gx.kafka.load.SimpleIncomeTopicsLoader;
import ru.gx.worker.*;

import javax.persistence.EntityManagerFactory;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
public class DbAdapter {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleWorker simpleWorker;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DbAdapterSettingsContainer settings;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleIncomeTopicsLoader incomeTopicsLoader;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private AllTradeEntityFromDtoConverter allTradesEntityFromDtoConverter;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DealEntityFromDtoConverter dealsEntityFromDtoConverter;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private OrderEntityFromDtoConverter ordersEntityFromDtoConverter;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SecurityEntityFromDtoConverter securitiesEntityFromDtoConverter;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private AllTradesRepository allTradesRepository;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DealsRepository dealsRepository;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private OrdersRepository ordersRepository;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SecuritiesRepository securitiesRepository;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private KafkaIncomeOffsetsRepository kafkaIncomeOffsetsRepository;

    @Getter
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private EntityManagerFactory entityManagerFactory;

    private SessionFactory sessionFactory;
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий Worker-а">

    /**
     * Обработка события о начале работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @SuppressWarnings("unused")
    @EventListener(SimpleStartingExecuteEvent.class)
    public void startingExecute(SimpleStartingExecuteEvent event) {
        log.debug("Starting startingExecute()");
        if (this.sessionFactory == null) {
            if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
                throw new NullPointerException("entityManagerFactory is not a hibernate factory!");
            }
            this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        }
        final var offsets = this.kafkaIncomeOffsetsRepository.findAll();

        final var partitionsOffset = new ArrayList<PartitionOffset>();
        for (var topicDescriptor : this.incomeTopicsLoader.getAll()) {
            partitionsOffset.clear();
            offsets.forEach(o -> {
                if (o.getTopic() != null && o.getTopic().equals(topicDescriptor.getTopic())) {
                    partitionsOffset.add(new PartitionOffset(o.getPartition(), o.getOffset()));
                }
            });
            if (partitionsOffset.size() <= 0) {
                this.incomeTopicsLoader.seekTopicAllPartitionsToBegin(topicDescriptor.getTopic());
            } else {
                this.incomeTopicsLoader.seekTopic(topicDescriptor.getTopic(), partitionsOffset);
            }
        }
        // workIncomeTopicsConfiguration.seekTopic();...
        // this.workIncomeTopicsConfiguration.seekAllToBegin();

        log.debug("Finished startingExecute()");
    }

    /**
     * Обработка события об окончании работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @SuppressWarnings("unused")
    @EventListener(SimpleStoppingExecuteEvent.class)
    public void stoppingExecute(SimpleStoppingExecuteEvent event) {
        log.debug("Starting stoppingExecute()");
        log.debug("Finished stoppingExecute()");
    }

    /**
     * Обработчик итераций.
     *
     * @param event Объект-событие с параметрами итерации.
     */
    @EventListener(SimpleIterationExecuteEvent.class)
    public void iterationExecute(SimpleIterationExecuteEvent event) {
        log.debug("Starting iterationExecute()");
        try {
            this.simpleWorker.runnerIsLifeSet();
            event.setImmediateRunNextIteration(false);

            final var session = this.sessionFactory.openSession();
            try (session) {
                final var tran = session.beginTransaction();
                try {
                    final var durationOnPoll = this.settings.getDurationOnPollMs();
                    this.incomeTopicsLoader.processAllTopics(durationOnPoll);

                    saveKafkaOffsets();

                    tran.commit();
                } catch (Exception e) {
                    tran.rollback();
                    internalTreatmentExceptionOnDataRead(event, e);
                }
            }
        } catch (Exception e) {
            internalTreatmentExceptionOnDataRead(event, e);
        } finally {
            log.debug("Finished iterationExecute()");
        }
    }

    /**
     * Обработка ошибки при выполнении итерации.
     *
     * @param event Объект-событие с параметрами итерации.
     * @param e     Ошибка, которую требуется обработать.
     */
    private void internalTreatmentExceptionOnDataRead(SimpleIterationExecuteEvent event, Exception e) {
        log.error("", e);
        if (e instanceof InterruptedException) {
            log.info("event.setStopExecution(true)");
            event.setStopExecution(true);
        } else {
            log.info("event.setNeedRestart(true)");
            event.setNeedRestart(true);
        }
    }

    /**
     * Сохранение в БД текущих смещений Kafka.
     */
    private void saveKafkaOffsets() {
        final var kafkaOffsets = new ArrayList<KafkaIncomeOffsetEntity>();
        final var pCount = this.incomeTopicsLoader.prioritiesCount();
        for (int p = 0; p < pCount; p++) {
            final var descriptorsByPriority =
                    this.incomeTopicsLoader.getByPriority(p);
            if (descriptorsByPriority == null) {
                throw new InvalidParameterException("Can't get descriptors by priority " + p);
            }
            descriptorsByPriority
                    .forEach(topicDescriptor ->
                    topicDescriptor.getPartitions().forEach(partition -> {
                        final var offset = new KafkaIncomeOffsetEntity()
                                .setReader(this.simpleWorker.getServiceName())
                                .setTopic(topicDescriptor.getTopic())
                                .setPartition(partition)
                                .setOffset(topicDescriptor.getOffset(partition));
                        kafkaOffsets.add(offset);
                    })
            );
        }

        final var started = System.currentTimeMillis();
        this.kafkaIncomeOffsetsRepository.saveAllAndFlush(kafkaOffsets);
        log.info("KafkaOffsets: saved {} rows in {} ms", kafkaOffsets.size(), System.currentTimeMillis() - started);
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий о чтении данных из Kafka">

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.model.internal.QuikSecurity}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedSecuritiesEvent.class)
    public void loadedSecurities(LoadedSecuritiesEvent event) throws InvalidDataObjectTypeException {
        log.debug("Starting loadedSecurities()");
        try {
            this.simpleWorker.runnerIsLifeSet();

            final var securityEntitiesPackage = new SecurityEntitiesPackage();
            this.securitiesEntityFromDtoConverter.fillEntitiesPackageFromDtoPackage(securityEntitiesPackage, event.getObjects());
            final var started = System.currentTimeMillis();
            this.securitiesRepository.saveAll(securityEntitiesPackage.getObjects());
            log.info("Securities: saved {} rows in {} ms", securityEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedSecurities()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.model.internal.QuikDeal}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedDealsEvent.class)
    public void loadedDeals(LoadedDealsEvent event) throws InvalidDataObjectTypeException {
        log.debug("Starting loadedDeals()");
        try {
            this.simpleWorker.runnerIsLifeSet();

            final var dealEntitiesPackage = new DealEntitiesPackage();
            this.dealsEntityFromDtoConverter.fillEntitiesPackageFromDtoPackage(dealEntitiesPackage, event.getObjects());
            final var started = System.currentTimeMillis();
            this.dealsRepository.saveAll(dealEntitiesPackage.getObjects());
            log.info("Deals: saved {} rows in {} ms", dealEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedDeals()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.model.internal.QuikOrder}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedOrdersEvent.class)
    public void loadedOrders(LoadedOrdersEvent event) throws InvalidDataObjectTypeException {
        log.debug("Starting loadedOrders()");
        try {
            this.simpleWorker.runnerIsLifeSet();

            final var orderEntitiesPackage = new OrderEntitiesPackage();
            this.ordersEntityFromDtoConverter.fillEntitiesPackageFromDtoPackage(orderEntitiesPackage, event.getObjects());

            final var started = System.currentTimeMillis();
            this.ordersRepository.saveAll(orderEntitiesPackage.getObjects());
            log.info("Orders: saved {} rows in {} ms", orderEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedOrders()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gx.fin.gate.quik.model.internal.QuikAllTrade}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedAllTradesEvent.class)
    public void loadedAllTrades(LoadedAllTradesEvent event) throws InvalidDataObjectTypeException {
        log.debug("Starting loadedAllTrades()");
        try {
            this.simpleWorker.runnerIsLifeSet();

            final var allTradesEntitiesPackage = new AllTradeEntitiesPackage();
            this.allTradesEntityFromDtoConverter.fillEntitiesPackageFromDtoPackage(allTradesEntitiesPackage, event.getObjects());
            final var started = System.currentTimeMillis();
            this.allTradesRepository.saveAll(allTradesEntitiesPackage.getObjects());
            log.info("AllTrades: saved {} rows in {} ms", allTradesEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedAllTrades()");
        }
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
