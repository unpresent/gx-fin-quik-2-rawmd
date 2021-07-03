package ru.gxfin.quik.events;

import ru.gxfin.common.worker.AbstractIterationExecuteEvent;

public class DbAdapterIterationExecuteEvent extends AbstractIterationExecuteEvent {
    public DbAdapterIterationExecuteEvent(Object source) {
        super(source);
    }
}
