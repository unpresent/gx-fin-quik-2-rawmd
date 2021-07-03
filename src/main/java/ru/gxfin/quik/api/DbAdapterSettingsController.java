package ru.gxfin.quik.api;

import ru.gxfin.common.settings.SettingsController;

public interface DbAdapterSettingsController extends SettingsController {
    String WAIT_ON_STOP_MS = "db_adapter.wait_on_stop_ms";
    String MIN_TIME_PER_ITERATION_MS = "db_adapter.min_time_per_iteration_ms";
    String TIMEOUT_LIFE_MS = "db_adapter.timeout_life_ms";

    String INCOME_TOPIC_ALLTRADES = "kafka.income_topic.all_trades";
    String INCOME_TOPIC_DEALS = "kafka.income_topic.deals";
    String INCOME_TOPIC_ORDERS = "kafka.income_topic.orders";

    int getWaitOnStopMs();
    int getMinTimePerIterationMs();
    int getTimeoutLifeMs();

    String getIncomeTopicAlltrades();
    String getIncomeTopicDeals();
    String getIncomeTopicOrders();
}
