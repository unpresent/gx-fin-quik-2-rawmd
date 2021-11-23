package ru.gx.fin.quik;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.gx.events.DoStartStandardEventsExecutorEvent;
import ru.gx.simpleworker.DoStartSimpleWorkerEvent;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final var context = new SpringApplicationBuilder(Application.class)
                // .web(WebApplicationType.NONE)
                .run(args);
        DoStartStandardEventsExecutorEvent.publish(context, context);
        DoStartSimpleWorkerEvent.publish(context, context);
    }
}
