package ru.gx.fin.quik.events;

import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;

public class LoadedSecuritiesEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedSecuritiesEvent(Object source) {
        super(source);
    }
}
