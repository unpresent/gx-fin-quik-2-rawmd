package ru.gx.fin.quik.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class ConfigurationPropertiesKafka {
    public static final String INCOME_TOPIC_ALL_TRADES = "kafka.income-topics.all-trades";
    public static final String INCOME_TOPIC_DEALS = "kafka.income-topics.deals";
    public static final String INCOME_TOPIC_ORDERS = "kafka.income-topics.orders";
    public static final String INCOME_TOPIC_SECURITIES = "kafka.income-topics.securities";

    @NestedConfigurationProperty
    private IncomeTopics incomeTopics = new IncomeTopics();

    @Getter
    @Setter
    private static class IncomeTopics {
        private String allTrades = "quikProviderAllTrades";
        private String deals = "quikProviderDeals";
        private String orders = "quikProviderOrders";
        private String securities = "quikProviderSecurities";
    }
}
