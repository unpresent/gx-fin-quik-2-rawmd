package ru.gxfin.quik.loading;

import org.jetbrains.annotations.NotNull;
import ru.gxfin.common.kafka.configuration.IncomeTopicsConfiguration;
import ru.gxfin.common.kafka.loader.IncomeTopicLoadingDescriptor;
import ru.gxfin.gate.quik.model.internal.QuikDeal;
import ru.gxfin.gate.quik.model.internal.QuikDealsPackage;
import ru.gxfin.gate.quik.model.internal.QuikOrder;
import ru.gxfin.gate.quik.model.internal.QuikOrdersPackage;

public class QuikDealsLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikDeal, QuikDealsPackage> {
    public QuikDealsLoadingDescriptor(@NotNull IncomeTopicsConfiguration configuration, @NotNull String topic) {
        super(configuration, topic);
    }
}
