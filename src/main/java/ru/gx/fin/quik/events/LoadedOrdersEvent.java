package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikOrder;
import ru.gx.fin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;

public class LoadedOrdersEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikOrder, QuikOrdersPackage> {
    public LoadedOrdersEvent(Object source) {
        super(source);
    }
}
