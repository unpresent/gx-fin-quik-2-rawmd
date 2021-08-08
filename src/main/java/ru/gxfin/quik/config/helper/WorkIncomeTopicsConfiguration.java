package ru.gxfin.quik.config.helper;

import org.springframework.context.ApplicationContext;
import ru.gxfin.common.kafka.configuration.AbstractIncomeTopicsConfiguration;

public class WorkIncomeTopicsConfiguration extends AbstractIncomeTopicsConfiguration {
    public WorkIncomeTopicsConfiguration(ApplicationContext context) {
        super(context);
    }
}
