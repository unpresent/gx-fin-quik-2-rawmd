package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikAllTrade;

public class LoadedAllTradesEvent extends AbstractObjectsLoadedFromIncomeTopicEvent<QuikAllTrade> {
    public LoadedAllTradesEvent(Object source) {
        super(source);
    }
}
