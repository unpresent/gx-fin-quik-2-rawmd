package ru.gx.fin.quik.events;

import ru.gx.fin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gx.fin.gate.quik.model.internal.QuikSecurity;
import ru.gx.kafka.events.AbstractOnObjectsLoadedFromIncomeTopicEvent;
import ru.gx.kafka.events.AbstractOnRawDataLoadedFromIncomeTopicEvent;
import ru.gx.kafka.events.OnRawDataLoadedFromIncomeTopicEvent;

public class LoadedSecuritiesEvent extends AbstractOnRawDataLoadedFromIncomeTopicEvent {
    public LoadedSecuritiesEvent(Object source) {
        super(source);
    }
}
