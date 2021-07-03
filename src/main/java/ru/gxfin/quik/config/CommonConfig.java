package ru.gxfin.quik.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.gxfin.quik.api.DbAdapter;
import ru.gxfin.quik.api.DbAdapterLifeController;
import ru.gxfin.quik.api.DbAdapterSettingsController;
import ru.gxfin.quik.dbadapter.DefaultDbAdapter;
import ru.gxfin.quik.dbadapter.DefaultDbAdapterLifeController;
import ru.gxfin.quik.dbadapter.DefaultDbAdapterSettingsController;
import ru.gxfin.quik.services.AllTradesService;
import ru.gxfin.quik.services.AllTradesServiceImpl;
import ru.gxfin.quik.services.DealsService;
import ru.gxfin.quik.services.DealsServiceImpl;

// @EnableJpaRepositories(basePackages = "ru.gxfin.common.repository")
public class CommonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Bean
    @Autowired
    public DbAdapterSettingsController dbAdapterSettingsController(ApplicationContext context) {
        return new DefaultDbAdapterSettingsController(context);
    }

    @Bean
    public DbAdapter dbAdapter() {
        return new DefaultDbAdapter();
    }

    @Bean
    public DbAdapterLifeController dbAdapterLifeController() {
        return new DefaultDbAdapterLifeController();
    }

    @Bean
    @Autowired
    public AllTradesService allTradesService(ObjectMapper objectMapper) {
        return new AllTradesServiceImpl(objectMapper);
    }

    @Bean
    @Autowired
    public DealsService dealsService(ObjectMapper objectMapper) {
        return new DealsServiceImpl(objectMapper);
    }

    @Bean
    public IncomeTopicsServices topicsServices() {
        return new IncomeTopicsServices();
    }
}
