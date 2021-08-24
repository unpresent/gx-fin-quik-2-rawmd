package ru.gxfin.quik.loading;

import org.jetbrains.annotations.NotNull;
import ru.gxfin.common.kafka.configuration.IncomeTopicsConfiguration;
import ru.gxfin.common.kafka.loader.IncomeTopicLoadingDescriptor;
import ru.gxfin.gate.quik.model.internal.QuikAllTrade;
import ru.gxfin.gate.quik.model.internal.QuikAllTradesPackage;
import ru.gxfin.gate.quik.model.internal.QuikDeal;
import ru.gxfin.gate.quik.model.internal.QuikDealsPackage;

public class QuikAllTradesLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikAllTrade, QuikAllTradesPackage> {
    public QuikAllTradesLoadingDescriptor(@NotNull IncomeTopicsConfiguration configuration, @NotNull String topic) {
        super(configuration, topic);
    }
}
