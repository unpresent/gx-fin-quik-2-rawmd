package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikOrder;
import ru.gx.fin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;

public class LoadedOrdersEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedOrdersEvent(Object source) {
        super(source);
    }
}
