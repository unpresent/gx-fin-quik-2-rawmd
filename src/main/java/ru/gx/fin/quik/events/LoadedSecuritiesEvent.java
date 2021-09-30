package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gx.fin.gate.quik.model.internal.QuikSecurity;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;

public class LoadedSecuritiesEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikSecurity, QuikSecuritiesPackage> {
    public LoadedSecuritiesEvent(Object source) {
        super(source);
    }
}
