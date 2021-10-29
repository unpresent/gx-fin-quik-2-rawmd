package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikDeal;
import ru.gx.fin.gate.quik.model.internal.QuikDealsPackage;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;

public class LoadedDealsEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedDealsEvent(Object source) {
        super(source);
    }
}
