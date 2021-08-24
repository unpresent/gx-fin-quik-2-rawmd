package ru.gxfin.quik.dbadapter;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.gxfin.common.settings.AbstractSettingsController;
import ru.gxfin.common.settings.UnknownApplicationSettingException;

import java.time.Duration;

public class DbAdapterSettingsController extends AbstractSettingsController {
    String WAIT_ON_STOP_MS = "db_adapter.wait_on_stop_ms";
    String WAIT_ON_RESTART_MS = "db_adapter.wait_on_restart_ms";
    String MIN_TIME_PER_ITERATION_MS = "db_adapter.min_time_per_iteration_ms";
    String TIMEOUT_LIFE_MS = "db_adapter.timeout_life_ms";

    String DURATION_ON_POLL_MS = "kafka.duration_on_poll_ms";

    String INCOME_TOPIC_ALLTRADES = "kafka.income_topic.all_trades";
    String INCOME_TOPIC_DEALS = "kafka.income_topic.deals";
    String INCOME_TOPIC_ORDERS = "kafka.income_topic.orders";
    String INCOME_TOPIC_SECURITIES = "kafka.income_topic.securities";

    public DbAdapterSettingsController(ApplicationContext context) throws UnknownApplicationSettingException {
        super(context);

        // TODO: Переписать на чтение настроек
        loadIntegerSetting(WAIT_ON_STOP_MS);
        loadIntegerSetting(WAIT_ON_RESTART_MS);
        loadIntegerSetting(MIN_TIME_PER_ITERATION_MS);
        loadIntegerSetting(TIMEOUT_LIFE_MS);
        loadIntegerSetting(DURATION_ON_POLL_MS);

        loadStringSetting(INCOME_TOPIC_ALLTRADES);
        loadStringSetting(INCOME_TOPIC_DEALS);
        loadStringSetting(INCOME_TOPIC_ORDERS);
        loadStringSetting(INCOME_TOPIC_SECURITIES);

    }

    public int getWaitOnStopMs() {
        return (Integer) getSetting(WAIT_ON_STOP_MS);
    }

    public int getWaitOnRestartMs() {
        return (Integer) getSetting(WAIT_ON_RESTART_MS);
    }

    public int getMinTimePerIterationMs() {
        return (Integer) getSetting(MIN_TIME_PER_ITERATION_MS);
    }

    public int getTimeoutLifeMs() {
        return (Integer) getSetting(TIMEOUT_LIFE_MS);
    }

    public Duration getDurationOnPollMs() {
        return Duration.ofMillis((Integer) this.getSetting(DURATION_ON_POLL_MS));
    }


    public String getIncomeTopicAlltrades() {
        return (String) getSetting(INCOME_TOPIC_ALLTRADES);
    }

    public String getIncomeTopicDeals() {
        return (String) getSetting(INCOME_TOPIC_DEALS);
    }

    public String getIncomeTopicOrders() {
        return (String) getSetting(INCOME_TOPIC_ORDERS);
    }

    public String getIncomeTopicSecurities() {
        return (String) getSetting(INCOME_TOPIC_SECURITIES);
    }
}
