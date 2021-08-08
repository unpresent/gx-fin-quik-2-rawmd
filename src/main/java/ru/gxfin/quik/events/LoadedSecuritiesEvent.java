package ru.gxfin.quik.events;

import ru.gxfin.common.kafka.events.AbstractObjectsLoadedFromIncomeTopicEvent;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;

public class LoadedSecuritiesEvent extends AbstractObjectsLoadedFromIncomeTopicEvent<QuikSecurity> {
    public LoadedSecuritiesEvent(Object source) {
        super(source);
    }
}
