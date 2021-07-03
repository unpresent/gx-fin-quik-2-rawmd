package ru.gxfin.quik.dbadapter;

import org.springframework.context.ApplicationContext;
import ru.gxfin.common.settings.AbstractSettingsController;
import ru.gxfin.quik.api.DbAdapterSettingsController;

public class DefaultDbAdapterSettingsController
        extends AbstractSettingsController
        implements DbAdapterSettingsController {
    public DefaultDbAdapterSettingsController(ApplicationContext context) {
        super(context);

        // TODO: Переписать на чтение настроек
        loadIntegerSetting(WAIT_ON_STOP_MS);
        loadIntegerSetting(MIN_TIME_PER_ITERATION_MS);
        loadIntegerSetting(TIMEOUT_LIFE_MS);

        loadStringSetting(INCOME_TOPIC_ALLTRADES);
        loadStringSetting(INCOME_TOPIC_DEALS);
        loadStringSetting(INCOME_TOPIC_ORDERS);
    }

    @Override
    public int getWaitOnStopMs() {
        return (Integer) getSetting(WAIT_ON_STOP_MS);
    }

    @Override
    public int getMinTimePerIterationMs() {
        return (Integer) getSetting(MIN_TIME_PER_ITERATION_MS);
    }

    @Override
    public int getTimeoutLifeMs() {
        return (Integer) getSetting(TIMEOUT_LIFE_MS);
    }

    @Override
    public String getIncomeTopicAlltrades() {
        return (String) getSetting(INCOME_TOPIC_ALLTRADES);
    }

    @Override
    public String getIncomeTopicDeals() {
        return (String) getSetting(INCOME_TOPIC_DEALS);
    }

    @Override
    public String getIncomeTopicOrders() {
        return (String) getSetting(INCOME_TOPIC_ORDERS);
    }
}
