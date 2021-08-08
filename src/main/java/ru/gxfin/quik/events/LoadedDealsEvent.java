package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikDeal;

public class LoadedDealsEvent extends AbstractObjectsLoadedFromIncomeTopicEvent<QuikDeal> {
    public LoadedDealsEvent(Object source) {
        super(source);
    }
}
