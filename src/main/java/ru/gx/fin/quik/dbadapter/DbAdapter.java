package ru.gx.fin.quik.dbadapter;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import ru.gx.data.ActiveConnectionsContainer;
import ru.gx.data.InvalidDataObjectTypeException;
import ru.gx.fin.quik.events.LoadedAllTradesEvent;
import ru.gx.fin.quik.events.LoadedDealsEvent;
import ru.gx.fin.quik.events.LoadedOrdersEvent;
import ru.gx.fin.quik.events.LoadedSecuritiesEvent;
import ru.gx.kafka.TopicDirection;
import ru.gx.kafka.load.*;
import ru.gx.kafka.offsets.TopicsOffsetsLoader;
import ru.gx.kafka.offsets.TopicsOffsetsSaver;
import ru.gx.worker.SimpleOnIterationExecuteEvent;
import ru.gx.worker.SimpleOnStartingExecuteEvent;
import ru.gx.worker.SimpleOnStoppingExecuteEvent;
import ru.gx.worker.SimpleWorker;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
public class DbAdapter {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Constants">
    private static final String callSaveAllTrades = "CALL \"Quik\".\"AllTrades@SavePackage\"(?)";
    private static final String callSaveOrders = "CALL \"Quik\".\"Orders@SavePackage\"(?)";
    private static final String callSaveDeals = "CALL \"Quik\".\"Deals@SavePackage\"(?)";
    private static final String callSaveSecurities = "CALL \"Quik\".\"Securities@SavePackage\"(?)";
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DataSource dataSource;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ActiveConnectionsContainer connectionsContainer;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleWorker simpleWorker;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DbAdapterSettingsContainer settings;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleIncomeTopicsConfiguration incomeTopicsConfiguration;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private StandardIncomeTopicsLoader incomeTopicsLoader;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private IncomeTopicsOffsetsController incomeTopicsOffsetsController;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private TopicsOffsetsLoader topicsOffsetsLoader;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private TopicsOffsetsSaver topicsOffsetsSaver;
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий Worker-а">

    private void saveData(@NotNull final String callProc, @NotNull final Collection<ConsumerRecord<?, ?>> data) {
        if (data.size() <= 0) {
            return;
        }
        final var timeStarted = System.currentTimeMillis();
        final var connection = this.connectionsContainer.getCurrent();
        try {
            if (connection == null) {
                throw new SQLException("Connection doesn't opened!");
            }
            try (final var stmt = this.connectionsContainer.getCurrent().prepareCall(callProc)) {
                var i = 0;
                for (var rec : data) {
                    this.simpleWorker.runnerIsLifeSet();
                    stmt.setString(1, (String) rec.value());
                    stmt.execute();
                    i++;
                }
                log.info("Executed: {} {} times in {} ms", callProc, i, System.currentTimeMillis() - timeStarted);
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }


    /**
     * Обработка события о начале работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @SneakyThrows(SQLException.class)
    @SuppressWarnings("unused")
    @EventListener(SimpleOnStartingExecuteEvent.class)
    public void startingExecute(SimpleOnStartingExecuteEvent event) {
        log.debug("Starting startingExecute()");

        try (var connection = getDataSource().getConnection()) {
            this.connectionsContainer.putCurrent(connection);
            final var offsets = this.topicsOffsetsLoader.loadOffsets(TopicDirection.In, this.incomeTopicsConfiguration.getReaderName());
            if (offsets.size() <= 0) {
                this.incomeTopicsOffsetsController.seekAllToBegin(this.incomeTopicsConfiguration);
            } else {
                this.incomeTopicsOffsetsController.seekTopicsByList(this.incomeTopicsConfiguration, offsets);
            }
        } finally {
            this.connectionsContainer.putCurrent(null);
        }
        log.debug("Finished startingExecute()");
    }

    /**
     * Обработка события об окончании работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @SuppressWarnings("unused")
    @EventListener(SimpleOnStoppingExecuteEvent.class)
    public void stoppingExecute(SimpleOnStoppingExecuteEvent event) {
        log.debug("Starting stoppingExecute()");
        log.debug("Finished stoppingExecute()");
    }

    /**
     * Обработчик итераций.
     *
     * @param event Объект-событие с параметрами итерации.
     */
    @EventListener(SimpleOnIterationExecuteEvent.class)
    public void iterationExecute(SimpleOnIterationExecuteEvent event) {
        log.debug("Starting iterationExecute()");
        try {
            this.simpleWorker.runnerIsLifeSet();
            event.setImmediateRunNextIteration(false);

            try (var connection = getDataSource().getConnection()) {
                this.connectionsContainer.putCurrent(connection);
                try {
                    final var durationOnPoll = this.settings.getDurationOnPollMs();
                    // Загружаем данные и сохраняем в БД
                    final var result = this.incomeTopicsLoader.processAllTopics(this.incomeTopicsConfiguration, durationOnPoll);
                    for (var c: result.values()) {
                        if (c.size() > 1) {
                            event.setImmediateRunNextIteration(true);
                            break;
                        }
                    }
                    // Сохраняем смещения
                    final var offsets = this.incomeTopicsOffsetsController.getOffsetsByConfiguration(this.incomeTopicsConfiguration);
                    this.topicsOffsetsSaver.saveOffsets(TopicDirection.In, this.incomeTopicsConfiguration.getReaderName(), offsets);

                } catch (Exception e) {
                    internalTreatmentExceptionOnDataRead(event, e);
                }
            } finally {
                this.connectionsContainer.putCurrent(null);
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
    private void internalTreatmentExceptionOnDataRead(SimpleOnIterationExecuteEvent event, Exception e) {
        log.error("", e);
        if (e instanceof InterruptedException) {
            log.info("event.setStopExecution(true)");
            event.setStopExecution(true);
        } else {
            log.info("event.setNeedRestart(true)");
            event.setNeedRestart(true);
        }
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
    public void loadedSecurities(LoadedSecuritiesEvent event) {
        if (event.getData().size() <= 0) {
            return;
        }
        log.debug("Starting loadedSecurities()");
        try {
            this.saveData(callSaveSecurities, event.getData());
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
    public void loadedDeals(LoadedDealsEvent event) {
        if (event.getData().size() <= 0) {
            return;
        }
        log.debug("Starting loadedDeals()");
        try {
            this.saveData(callSaveDeals, event.getData());
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
    public void loadedOrders(LoadedOrdersEvent event) {
        if (event.getData().size() <= 0) {
            return;
        }
        log.debug("Starting loadedOrders()");
        try {
            this.saveData(callSaveOrders, event.getData());
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
    public void loadedAllTrades(LoadedAllTradesEvent event) {
        if (event.getData().size() <= 0) {
            return;
        }
        log.debug("Starting loadedAllTrades()");
        try {
            this.saveData(callSaveAllTrades, event.getData());
        } finally {
            log.debug("Finished loadedAllTrades()");
        }
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
