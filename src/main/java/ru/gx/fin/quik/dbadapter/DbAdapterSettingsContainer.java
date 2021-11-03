package ru.gx.fin.quik.dbadapter;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.fin.quik.config.ConfigurationPropertiesKafka;
import ru.gx.settings.SimpleSettingsController;
import ru.gx.settings.UnknownApplicationSettingException;

import javax.annotation.PostConstruct;
import java.time.Duration;

import static lombok.AccessLevel.PROTECTED;

public class DbAdapterSettingsContainer {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    @NotNull
    private SimpleSettingsController simpleSettingsController;

    @PostConstruct
    public void init() throws UnknownApplicationSettingException {
        this.simpleSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ALL_TRADES);
        this.simpleSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_DEALS);
        this.simpleSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ORDERS);
        this.simpleSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_SECURITIES);
    }

    public String getIncomeTopicAllTrades() {
        return this.simpleSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ALL_TRADES);
    }

    public String getIncomeTopicDeals() {
        return this.simpleSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_DEALS);
    }

    public String getIncomeTopicOrders() {
        return this.simpleSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ORDERS);
    }

    public String getIncomeTopicSecurities() {
        return this.simpleSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_SECURITIES);
    }
}
