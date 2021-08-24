package ru.gxfin.quik;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.gxfin.quik.events.DbAdapterStartEvent;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final var context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);
        context.publishEvent(new DbAdapterStartEvent("Application"));

        /*
        final var allTrade = new AllTrade();
        allTrade.setRowIndex(1);
        allTrade.setTradeNum("001100");
        allTrade.setFlags(0);
        allTrade.setTradeDateTime(LocalDateTime.parse("2021-06-21T23:30:50"));
        allTrade.setExchangeCode("MOEX");
        allTrade.setSecCode("LKOH");

        final var allTradesService = context.getBean("allTradesService", AllTradesService.class);
        allTradesService.addAllTrade(allTrade);

        //*/
    }
}
