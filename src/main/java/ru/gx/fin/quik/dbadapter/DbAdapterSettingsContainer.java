package ru.gx.fin.quik.dbadapter;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.settings.StandardSettingsController;
import ru.gx.core.settings.UnknownApplicationSettingException;
import ru.gx.fin.quik.config.ConfigurationPropertiesServiceKafka;

import javax.annotation.PostConstruct;

import static lombok.AccessLevel.PROTECTED;

public class DbAdapterSettingsContainer {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    @NotNull
    private StandardSettingsController standardSettingsController;

    @PostConstruct
    public void init() throws UnknownApplicationSettingException {
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_ALL_TRADES);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_DEALS);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_ORDERS);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_SECURITIES);
    }

    public String getIncomeTopicAllTrades() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_ALL_TRADES);
    }

    public String getIncomeTopicDeals() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_DEALS);
    }

    public String getIncomeTopicOrders() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_ORDERS);
    }

    public String getIncomeTopicSecurities() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceKafka.INCOME_TOPIC_SECURITIES);
    }
}
