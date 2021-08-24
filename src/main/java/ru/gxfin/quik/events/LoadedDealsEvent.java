package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikDeal;
import ru.gxfin.gate.quik.model.internal.QuikDealsPackage;

public class LoadedDealsEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikDeal, QuikDealsPackage> {
    public LoadedDealsEvent(Object source) {
        super(source);
    }
}
