package ru.gx.fin.quik.descriptors;

import org.jetbrains.annotations.NotNull;
import ru.gx.fin.gate.quik.model.internal.QuikDeal;
import ru.gx.fin.gate.quik.model.internal.QuikDealsPackage;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptor;
import ru.gx.kafka.load.IncomeTopicLoadingDescriptorsDefaults;

public class QuikDealsLoadingDescriptor extends IncomeTopicLoadingDescriptor<QuikDeal, QuikDealsPackage> {
    public QuikDealsLoadingDescriptor(@NotNull final String topic, final IncomeTopicLoadingDescriptorsDefaults defaults) {
        super(topic, defaults);
    }
}
