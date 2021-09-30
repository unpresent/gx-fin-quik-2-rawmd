package ru.gx.fin.quik.descriptors;

import org.jetbrains.annotations.NotNull;
import ru.gx.fin.gate.quik.model.internal.QuikOrder;
import ru.gx.fin.gate.quik.model.internal.QuikOrdersPackage;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptor;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptorsDefaults;

public class QuikOrdersLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikOrder, QuikOrdersPackage> {
    public QuikOrdersLoadingDescriptor(@NotNull final String topic, final IncomeTopicLoadingDescriptorsDefaults defaults) {
        super(topic, defaults);
    }
}
