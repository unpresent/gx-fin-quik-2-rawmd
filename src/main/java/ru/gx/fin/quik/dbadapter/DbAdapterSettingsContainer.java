package ru.gx.fin.quik.dbadapter;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.fin.quik.config.ConfigurationPropertiesKafka;
import ru.gx.settings.StandardSettingsController;
import ru.gx.settings.UnknownApplicationSettingException;

import javax.annotation.PostConstruct;

import static lombok.AccessLevel.PROTECTED;

public class DbAdapterSettingsContainer {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    @NotNull
    private StandardSettingsController standardSettingsController;

    @PostConstruct
    public void init() throws UnknownApplicationSettingException {
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ALL_TRADES);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_DEALS);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ORDERS);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_SECURITIES);
    }

    public String getIncomeTopicAllTrades() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ALL_TRADES);
    }

    public String getIncomeTopicDeals() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_DEALS);
    }

    public String getIncomeTopicOrders() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_ORDERS);
    }

    public String getIncomeTopicSecurities() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesKafka.INCOME_TOPIC_SECURITIES);
    }
}
