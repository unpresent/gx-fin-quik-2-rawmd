package ru.gx.fin.quik.dbadapter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import ru.gx.fin.quik.events.DbAdapterStartEvent;
import ru.gx.fin.quik.events.DbAdapterStopEvent;
import ru.gx.worker.SimpleWorker;

import static lombok.AccessLevel.*;

@Slf4j
public class DbAdapterLifeController {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleWorker simpleWorker;

    /**
     * Обработчик команды о запуске провайдера
     * @param event команда о запуске провайдера
     */
    @EventListener(DbAdapterStartEvent.class)
    public void onEvent(DbAdapterStartEvent event) {
        log.info("Starting onEvent(ProviderStartEvent event)");
        this.simpleWorker.start();
        log.info("Finished onEvent(ProviderStartEvent event)");
    }

    /**
     * Обработчик команды об остановке провайдера
     * @param event команда об остановке провайдера
     */
    @EventListener(DbAdapterStopEvent.class)
    public void onEvent(DbAdapterStopEvent event) {
        log.info("Starting onEvent(ProviderStopEvent event)");
        this.simpleWorker.stop();
        log.info("Finished onEvent(ProviderStopEvent event)");
    }
}
