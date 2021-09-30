package ru.gx.fin.quik.descriptors;

import org.jetbrains.annotations.NotNull;
import ru.gx.fin.gate.quik.model.internal.QuikAllTrade;
import ru.gx.fin.gate.quik.model.internal.QuikAllTradesPackage;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptor;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptorsDefaults;

public class QuikAllTradesLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikAllTrade, QuikAllTradesPackage> {
    public QuikAllTradesLoadingDescriptor(@NotNull final String topic, final IncomeTopicLoadingDescriptorsDefaults defaults) {
        super(topic, defaults);
    }
}
