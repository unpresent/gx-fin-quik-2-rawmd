package ru.gxfin.quik.loading;

import org.jetbrains.annotations.NotNull;
import ru.gxfin.common.kafka.configuration.IncomeTopicsConfiguration;
import ru.gxfin.common.kafka.loader.IncomeTopicLoadingDescriptor;
import ru.gxfin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gxfin.gate.quik.model.internal.QuikSecurity;

public class QuikSecuritiesLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikSecurity, QuikSecuritiesPackage> {
    public QuikSecuritiesLoadingDescriptor(@NotNull IncomeTopicsConfiguration configuration, @NotNull String topic) {
        super(configuration, topic);
    }
}
