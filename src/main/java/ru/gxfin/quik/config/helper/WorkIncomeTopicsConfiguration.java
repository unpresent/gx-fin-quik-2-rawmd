package ru.gxfin.quik.config.helper;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.gxfin.common.kafka.configuration.AbstractIncomeTopicsConfiguration;

public class WorkIncomeTopicsConfiguration extends AbstractIncomeTopicsConfiguration {
    public WorkIncomeTopicsConfiguration() {
        super();
    }
}
