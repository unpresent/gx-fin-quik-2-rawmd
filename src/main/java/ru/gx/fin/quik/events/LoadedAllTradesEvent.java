package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikAllTrade;
import ru.gx.fin.gate.quik.model.internal.QuikAllTradesPackage;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;

public class LoadedAllTradesEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikAllTrade, QuikAllTradesPackage> {
    public LoadedAllTradesEvent(Object source) {
        super(source);
    }
}
