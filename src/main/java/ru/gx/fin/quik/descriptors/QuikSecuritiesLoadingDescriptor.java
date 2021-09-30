package ru.gx.fin.quik.descriptors;

import org.jetbrains.annotations.NotNull;
import ru.gx.fin.gate.quik.model.internal.QuikSecuritiesPackage;
import ru.gx.fin.gate.quik.model.internal.QuikSecurity;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptor;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptorsDefaults;

public class QuikSecuritiesLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikSecurity, QuikSecuritiesPackage> {
    public QuikSecuritiesLoadingDescriptor(@NotNull final String topic, final IncomeTopicLoadingDescriptorsDefaults defaults) {
        super(topic, defaults);
    }
}
