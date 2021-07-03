package ru.gxfin.quik.dbadapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import ru.gxfin.quik.api.DbAdapterLifeController;
import ru.gxfin.quik.events.DbAdapterStartEvent;
import ru.gxfin.quik.events.DbAdapterStopEvent;

@Slf4j
public class DefaultDbAdapterLifeController implements DbAdapterLifeController {
    @Autowired
    private DefaultDbAdapter dbAdapter;

    @EventListener(DbAdapterStartEvent.class)
    @Override
    public void onEvent(DbAdapterStartEvent event) {
        log.info("Starting onEvent(ProviderStartEvent event)");
        this.dbAdapter.start();
        log.info("Finished onEvent(ProviderStartEvent event)");
    }

    @EventListener(DbAdapterStopEvent.class)
    @Override
    public void onEvent(DbAdapterStopEvent event) {
        log.info("Starting onEvent(ProviderStopEvent event)");
        this.dbAdapter.stop();
        log.info("Finished onEvent(ProviderStopEvent event)");
    }
}
