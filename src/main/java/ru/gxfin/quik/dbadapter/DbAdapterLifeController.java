package ru.gxfin.quik.dbadapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.context.event.EventListener;
import ru.gxfin.quik.events.DbAdapterStartEvent;
import ru.gxfin.quik.events.DbAdapterStopEvent;

@Slf4j
public class DbAdapterLifeController {
    @Autowired
    private DbAdapter dbAdapter;

    /**
     * Обработчик команды о запуске провайдера
     * @param event команда о запуске провайдера
     */
    @EventListener(DbAdapterStartEvent.class)
    public void onEvent(DbAdapterStartEvent event) {
        log.info("Starting onEvent(ProviderStartEvent event)");
        this.dbAdapter.start();
        log.info("Finished onEvent(ProviderStartEvent event)");
    }

    /**
     * Обработчик команды об остановке провайдера
     * @param event команда об остановке провайдера
     */
    @EventListener(DbAdapterStopEvent.class)
    public void onEvent(DbAdapterStopEvent event) {
        log.info("Starting onEvent(ProviderStopEvent event)");
        this.dbAdapter.stop();
        log.info("Finished onEvent(ProviderStopEvent event)");
    }
}
