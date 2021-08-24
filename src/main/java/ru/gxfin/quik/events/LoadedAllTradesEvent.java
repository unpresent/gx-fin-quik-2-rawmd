package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikAllTrade;
import ru.gxfin.gate.quik.model.internal.QuikAllTradesPackage;

public class LoadedAllTradesEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikAllTrade, QuikAllTradesPackage> {
    public LoadedAllTradesEvent(Object source) {
        super(source);
    }
}
