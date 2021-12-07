package ru.gx.fin.quik.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "service.kafka")
@Getter
@Setter
public class ConfigurationPropertiesServiceKafka {
    public static final String INCOME_TOPIC_ALL_TRADES = "service.kafka.income-topics.all-trades";
    public static final String INCOME_TOPIC_DEALS = "service.kafka.income-topics.deals";
    public static final String INCOME_TOPIC_ORDERS = "service.kafka.income-topics.orders";
    public static final String INCOME_TOPIC_SECURITIES = "service.kafka.income-topics.securities";

    public static final String INCOME_TOPIC_ALL_TRADES_DEFAULT_VALUE = "quik.provider-all-trades";
    public static final String INCOME_TOPIC_DEALS_DEFAULT_VALUE = "quik.provider-deals";
    public static final String INCOME_TOPIC_ORDERS_DEFAULT_VALUE = "quik.provider-orders";
    public static final String INCOME_TOPIC_SECURITIES_DEFAULT_VALUE = "quik.provider-securities";

    @NestedConfigurationProperty
    private IncomeTopics incomeTopics = new IncomeTopics();

    @Getter
    @Setter
    private static class IncomeTopics {
        private String allTrades = INCOME_TOPIC_ALL_TRADES_DEFAULT_VALUE;
        private String deals = INCOME_TOPIC_DEALS_DEFAULT_VALUE;
        private String orders = INCOME_TOPIC_ORDERS_DEFAULT_VALUE;
        private String securities = INCOME_TOPIC_SECURITIES_DEFAULT_VALUE;
    }
}
