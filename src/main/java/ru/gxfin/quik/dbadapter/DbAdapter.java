package ru.gxfin.quik.dbadapter;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import ru.gxfin.common.data.ObjectCreateException;
import ru.gxfin.common.kafka.loader.IncomeTopicsLoader;
import ru.gxfin.common.worker.AbstractIterationExecuteEvent;
import ru.gxfin.common.worker.AbstractStartingExecuteEvent;
import ru.gxfin.common.worker.AbstractStoppingExecuteEvent;
import ru.gxfin.common.worker.AbstractWorker;
import ru.gxfin.quik.config.helper.WorkIncomeTopicsConfiguration;
import ru.gxfin.quik.entities.*;
import ru.gxfin.quik.events.*;
import ru.gxfin.quik.repositories.*;
import ru.gxfin.quik.services.AllTradesTransformer;
import ru.gxfin.quik.services.DealsTransformer;
import ru.gxfin.quik.services.OrdersTransformer;
import ru.gxfin.quik.services.SeciritiesTransformer;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;

@Slf4j
public class DbAdapter extends AbstractWorker {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @Autowired
    private DbAdapterSettingsController settings;

    @Autowired
    private WorkIncomeTopicsConfiguration workIncomeTopicsConfiguration;

    @Autowired
    private IncomeTopicsLoader incomeTopicsLoader;

    @Autowired
    private AllTradesTransformer allTradesTransformer;

    @Autowired
    private DealsTransformer dealsTransformer;

    @Autowired
    private OrdersTransformer ordersTransformer;

    @Autowired
    private SeciritiesTransformer seciritiesTransformer;

    @Autowired
    private AllTradesRepository allTradesRepository;

    @Autowired
    private DealsRepository dealsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private SecuritiesRepository securitiesRepository;

    @Autowired
    private KafkaOffsetsRepository kafkaOffsetsRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private SessionFactory sessionFactory;

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Settings">
    @EventListener
    public void onEventChangedSettings(DbAdpterSettingsChangedEvent event) {
        log.info("onEventChangedSettings({})", event.getSettingName());
    }

    @Override
    protected int getMinTimePerIterationMs() {
        return this.settings.getMinTimePerIterationMs();
    }

    @Override
    protected int getTimoutRunnerLifeMs() {
        return this.settings.getTimeoutLifeMs();
    }

    @Override
    public int getWaitOnStopMs() {
        return this.settings.getWaitOnStopMs();
    }

    @Override
    public int getWaitOnRestartMs() {
        return this.settings.getWaitOnRestartMs();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Инициализация">
    public DbAdapter(String serviceName) {
        super(serviceName);
    }

    @Override
    protected AbstractIterationExecuteEvent createIterationExecuteEvent() {
        return new DbAdapterIterationExecuteEvent(this);
    }

    @Override
    protected AbstractStartingExecuteEvent createStartingExecuteEvent() {
        return new DbAdapterStartingExecuteEvent(this);
    }

    @Override
    protected AbstractStoppingExecuteEvent createStoppingExecuteEvent() {
        return new DbAdapterStoppingExecuteEvent(this);
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий Worker-а">

    /**
     * Обработка события о начале работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(DbAdapterStartingExecuteEvent.class)
    public void startingExecute(DbAdapterStartingExecuteEvent event) {
        log.debug("Starting startingExecute()");
        if (this.sessionFactory == null) {
            if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
                throw new NullPointerException("entityManagerFactory is not a hibernate factory!");
            }
            this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        }
        final var offsets = this.kafkaOffsetsRepository.findAll();


        this.workIncomeTopicsConfiguration.seekAllToBegin();
        log.debug("Finished startingExecute()");
    }

    /**
     * Обработка события об окончании работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(DbAdapterStoppingExecuteEvent.class)
    public void stoppingExecute(DbAdapterStoppingExecuteEvent event) {
        log.debug("Starting stoppingExecute()");
        log.debug("Finished stoppingExecute()");
    }

    /**
     * Обработчик итераций.
     *
     * @param event Объект-событие с параметрами итерации.
     */
    @EventListener(DbAdapterIterationExecuteEvent.class)
    public void iterationExecute(DbAdapterIterationExecuteEvent event) {
        log.debug("Starting iterationExecute()");
        try {
            runnerIsLifeSet();
            event.setImmediateRunNextIteration(false);

            final var session = this.sessionFactory.openSession();
            try (session) {
                final var tran = session.beginTransaction();
                try {
                    final var durationOnPoll = this.settings.getDurationOnPollMs();
                    this.incomeTopicsLoader.loadTopicsByConfiguration(this.workIncomeTopicsConfiguration, durationOnPoll);

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
    private void internalTreatmentExceptionOnDataRead(DbAdapterIterationExecuteEvent event, Exception e) {
        log.error(e.getMessage());
        log.error(e.getStackTrace().toString());
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
        final var kafkaOffsets = new ArrayList<KafkaOffsetEntity>();
        final var pCount = this.workIncomeTopicsConfiguration.prioritiesCount();
        for (int p = 0; p < pCount; p++) {
            //noinspection unchecked
            this.workIncomeTopicsConfiguration
                    .getByPriority(p)
                    .forEach(topicDescriptor ->
                            topicDescriptor.getPartitionOffsets().forEach((k, v) -> {
                                final var offset = new KafkaOffsetEntity()
                                        .setReader(this.getName())
                                        .setTopic(topicDescriptor.getTopic())
                                        .setPartition((int) k)
                                        .setOffset((long) v);
                                kafkaOffsets.add(offset);
                            })
                    );
        }

        final var started = System.currentTimeMillis();
        this.kafkaOffsetsRepository.saveAllAndFlush(kafkaOffsets);
        log.info("KafkaOffsets: saved {} rows in {} ms", kafkaOffsets.size(), System.currentTimeMillis() - started);
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий о чтении данных из Kafka">

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gxfin.gate.quik.model.internal.QuikSecurity}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedSecuritiesEvent.class)
    public void loadedSecurities(LoadedSecuritiesEvent event) throws ObjectCreateException {
        log.debug("Starting loadedSecurities()");
        try {
            runnerIsLifeSet();

            final var securityEntitiesPackage = new SecurityEntitiesPackage();
            this.seciritiesTransformer.setEntitiesPackageFromDtoPackage(securityEntitiesPackage, event.getObjects());
            final var started = System.currentTimeMillis();
            this.securitiesRepository.saveAll(securityEntitiesPackage.getObjects());
            log.info("Securities: saved {} rows in {} ms", securityEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedSecurities()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gxfin.gate.quik.model.internal.QuikDeal}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedDealsEvent.class)
    public void loadedDeals(LoadedDealsEvent event) throws ObjectCreateException {
        log.debug("Starting loadedDeals()");
        try {
            runnerIsLifeSet();

            final var dealEntitiesPackage = new DealEntitiesPackage();
            this.dealsTransformer.setEntitiesPackageFromDtoPackage(dealEntitiesPackage, event.getObjects());
            final var started = System.currentTimeMillis();
            this.dealsRepository.saveAll(dealEntitiesPackage.getObjects());
            log.info("Deals: saved {} rows in {} ms", dealEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedDeals()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gxfin.gate.quik.model.internal.QuikOrder}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedOrdersEvent.class)
    public void loadedOrders(LoadedOrdersEvent event) throws ObjectCreateException {
        log.debug("Starting loadedOrders()");
        try {
            runnerIsLifeSet();

            final var orderEntitiesPackage = new OrderEntitiesPackage();
            this.ordersTransformer.setEntitiesPackageFromDtoPackage(orderEntitiesPackage, event.getObjects());
            final var started = System.currentTimeMillis();
            this.ordersRepository.saveAll(orderEntitiesPackage.getObjects());
            log.info("Orders: saved {} rows in {} ms", orderEntitiesPackage.size(), System.currentTimeMillis() - started);
        } finally {
            log.debug("Finished loadedOrders()");
        }
    }

    /**
     * Обработка события о загрузке из Kafka набора объектов {@link ru.gxfin.gate.quik.model.internal.QuikAllTrade}.
     *
     * @param event Объект-событие с параметрами.
     */
    @EventListener(LoadedAllTradesEvent.class)
    public void loadedAllTrades(LoadedAllTradesEvent event) throws ObjectCreateException {
        log.debug("Starting loadedAllTrades()");
        try {
            runnerIsLifeSet();

            final var allTradesEntitiesPackage = new AllTradeEntitiesPackage();
            this.allTradesTransformer.setEntitiesPackageFromDtoPackage(allTradesEntitiesPackage, event.getObjects());
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
