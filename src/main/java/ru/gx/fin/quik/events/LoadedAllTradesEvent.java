package ru.gx.fin.quik.events;

import ru.gx.core.events.AbstractDataEvent;

public class LoadedAllTradesEvent extends AbstractDataEvent {
    public LoadedAllTradesEvent(Object source) {
        super(source);
    }
}
