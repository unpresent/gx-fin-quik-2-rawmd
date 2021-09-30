package ru.gx.fin.quik.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * Событие-сигнал о необходимости перезапустить DbAdapter
 * @since 1.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class DbAdapterStartEvent extends ApplicationEvent {
    public DbAdapterStartEvent(Object source) {
        super(source);
    }
}