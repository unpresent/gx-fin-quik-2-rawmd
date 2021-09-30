package ru.gx.fin.quik.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * Событие-сигнал о необходимости остановить DbAdapter
 * @since 1.0
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class DbAdapterStopEvent extends ApplicationEvent {
    public DbAdapterStopEvent(Object source) {
        super(source);
    }
}