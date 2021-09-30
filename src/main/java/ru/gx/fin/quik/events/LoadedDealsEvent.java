package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikDeal;
import ru.gx.fin.gate.quik.model.internal.QuikDealsPackage;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;

public class LoadedDealsEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikDeal, QuikDealsPackage> {
    public LoadedDealsEvent(Object source) {
        super(source);
    }
}
