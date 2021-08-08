package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikOrder;

public class LoadedOrdersEvent extends AbstractObjectsLoadedFromIncomeTopicEvent<QuikOrder> {
    public LoadedOrdersEvent(Object source) {
        super(source);
    }
}
