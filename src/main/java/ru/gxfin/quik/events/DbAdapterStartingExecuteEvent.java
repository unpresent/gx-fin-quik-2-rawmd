package ru.gxfin.quik.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import ru.gxfin.common.worker.AbstractStartingExecuteEvent;

/**
 * Событие-сигнал о необходимости перезапустить DbAdapter
 * @since 1.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class DbAdapterStartingExecuteEvent extends AbstractStartingExecuteEvent {
    public DbAdapterStartingExecuteEvent(Object source) {
        super(source);
    }
}