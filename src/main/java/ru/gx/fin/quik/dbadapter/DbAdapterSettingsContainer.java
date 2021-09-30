package ru.gx.fin.quik.dbadapter;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.settings.SimpleSettingsController;
import ru.gx.settings.UnknownApplicationSettingException;

import javax.annotation.PostConstruct;
import java.time.Duration;

import static lombok.AccessLevel.PROTECTED;

public class DbAdapterSettingsContainer {
    String DURATION_ON_POLL_MS = "kafka.duration_on_poll_ms";
    String INCOME_TOPIC_ALL_TRADES = "kafka.income_topic.all_trades";
    String INCOME_TOPIC_DEALS = "kafka.income_topic.deals";
    String INCOME_TOPIC_ORDERS = "kafka.income_topic.orders";
    String INCOME_TOPIC_SECURITIES = "kafka.income_topic.securities";

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    @NotNull
    private SimpleSettingsController simpleSettingsController;

    @PostConstruct
    public void init() throws UnknownApplicationSettingException {
        this.simpleSettingsController.loadIntegerSetting(DURATION_ON_POLL_MS);
        this.simpleSettingsController.loadStringSetting(INCOME_TOPIC_ALL_TRADES);
        this.simpleSettingsController.loadStringSetting(INCOME_TOPIC_DEALS);
        this.simpleSettingsController.loadStringSetting(INCOME_TOPIC_ORDERS);
        this.simpleSettingsController.loadStringSetting(INCOME_TOPIC_SECURITIES);
    }

    public Duration getDurationOnPollMs() {
        return Duration.ofMillis(this.simpleSettingsController.getIntegerSetting(DURATION_ON_POLL_MS));
    }

    public String getIncomeTopicAllTrades() {
        return this.simpleSettingsController.getStringSetting(INCOME_TOPIC_ALL_TRADES);
    }

    public String getIncomeTopicDeals() {
        return this.simpleSettingsController.getStringSetting(INCOME_TOPIC_DEALS);
    }

    public String getIncomeTopicOrders() {
        return this.simpleSettingsController.getStringSetting(INCOME_TOPIC_ORDERS);
    }

    public String getIncomeTopicSecurities() {
        return this.simpleSettingsController.getStringSetting(INCOME_TOPIC_SECURITIES);
    }
}
