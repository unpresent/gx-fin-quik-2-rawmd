package ru.gx.fin.quik.events;

import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;

public class LoadedDealsEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedDealsEvent(Object source) {
        super(source);
    }
}
