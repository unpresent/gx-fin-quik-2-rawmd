package ru.gxfin.quik.dbadapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import ru.gxfin.common.worker.AbstractIterationExecuteEvent;
import ru.gxfin.common.worker.AbstractWorker;
import ru.gxfin.quik.api.DbAdapter;
import ru.gxfin.quik.api.DbAdapterSettingsController;
import ru.gxfin.quik.config.IncomeTopicsServices;
import ru.gxfin.quik.events.DbAdapterIterationExecuteEvent;
import ru.gxfin.quik.events.DbAdpterSettingsChangedEvent;
import ru.gxfin.quik.services.AllTradesService;

import java.time.Duration;

@Slf4j
public class DefaultDbAdapter extends AbstractWorker implements DbAdapter {
    public DefaultDbAdapter() {
        super(DefaultDbAdapter.class.getSimpleName());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @Autowired
    private DbAdapterSettingsController settings;

    @Autowired
    private AllTradesService allTradesService;

    @Autowired
    private Consumer<Long, String> consumer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IncomeTopicsServices incomeTopicsServices;
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
    public int getWaitOnStopMS() {
        return this.settings.getWaitOnStopMs();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    @Override
    protected AbstractIterationExecuteEvent createIterationExecuteEvent() {
        return new DbAdapterIterationExecuteEvent(this);
    }

    @EventListener(DbAdapterIterationExecuteEvent.class)
    public void iterationExecute(DbAdapterIterationExecuteEvent event) {
        log.debug("Starting iterationExecute()");
        try {
            runnerIsLifeSet();

            ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(500));
            log.info("polled.count() == {}", consumerRecords.count());
            //*
            if (!consumerRecords.isEmpty()) {
                for (var rec : consumerRecords) {
                    final var service = this.incomeTopicsServices.get(rec.topic());
                    final var dataPackage = service.addJsonPackage(rec.value());
                    final var n = dataPackage.size();
                    log.info("Received: package == {}, packageSize == {}", dataPackage.getClass().getSimpleName(), dataPackage.size());
                }
                event.setImmediateRunNextIteration(true);
            } else {
                event.setImmediateRunNextIteration(false);
            }
            //*/

            /*
            if (isFirst) {
                isFirst = false;
                final var t1 = new AllTradeEntity();
                t1.setTradeNum("001100");
                t1.setFlags(0);
                t1.setTradeDateTime(LocalDateTime.parse("2021-06-21T23:30:50"));
                t1.setExchangeCode("MOEX");
                t1.setSecCode("LKOH");

                allTradesService.add(t1);

                final var t2 = allTradesService.findByExchangeCodeAndTradeNum("MOEX", "001100");
                System.out.println(t2.getId());
            }
            //*/
        } catch (Exception e) {
            internalTreatmentExceptionOnDataRead(event, e);
        } finally {
            log.debug("Finished iterationExecute()");
        }
    }

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
}
