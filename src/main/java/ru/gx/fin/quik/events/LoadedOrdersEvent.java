package ru.gx.fin.quik.events;

import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;

public class LoadedOrdersEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedOrdersEvent(Object source) {
        super(source);
    }
}
