package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikOrder;
import ru.gxfin.gate.quik.model.internal.QuikOrdersPackage;

public class LoadedOrdersEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikOrder, QuikOrdersPackage> {
    public LoadedOrdersEvent(Object source) {
        super(source);
    }
}
