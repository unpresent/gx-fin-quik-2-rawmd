package ru.gx.fin.quik.events;

import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;

public class LoadedAllTradesEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedAllTradesEvent(Object source) {
        super(source);
    }
}
