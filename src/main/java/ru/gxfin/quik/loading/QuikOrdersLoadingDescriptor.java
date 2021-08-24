package ru.gxfin.quik.loading;

import org.jetbrains.annotations.NotNull;
import ru.gxfin.common.kafka.configuration.IncomeTopicsConfiguration;
import ru.gxfin.common.kafka.loader.IncomeTopicLoadingDescriptor;
import ru.gxfin.gate.quik.model.internal.QuikOrder;
import ru.gxfin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gxfin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;

public class QuikOrdersLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikOrder, QuikOrdersPackage> {
    public QuikOrdersLoadingDescriptor(@NotNull IncomeTopicsConfiguration configuration, @NotNull String topic) {
        super(configuration, topic);
    }
}
