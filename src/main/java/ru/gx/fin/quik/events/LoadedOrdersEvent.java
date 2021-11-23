package ru.gx.fin.quik.events;

import ru.gx.events.AbstractDataEvent;

public class LoadedOrdersEvent extends AbstractDataEvent {
    public LoadedOrdersEvent(Object source) {
        super(source);
    }
}
