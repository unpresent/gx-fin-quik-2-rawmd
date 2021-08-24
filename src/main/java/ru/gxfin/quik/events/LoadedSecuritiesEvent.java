package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;

public class LoadedSecuritiesEvent extends AbstractOnObjectsLoadedFromIncomeTopicEvent<QuikSecurity, QuikSecuritiesPackage> {
    public LoadedSecuritiesEvent(Object source) {
        super(source);
    }
}
