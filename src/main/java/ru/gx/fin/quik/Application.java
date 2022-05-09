package ru.gx.fin.quik;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.gx.core.kafka.listener.DoStartKafkaSimpleListenerEvent;
import ru.gx.core.messaging.DoStartStandardMessagesExecutorEvent;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final var context = new SpringApplicationBuilder(Application.class)
                // .web(WebApplicationType.NONE)
                .run(args);
        DoStartStandardMessagesExecutorEvent.publish(context, context);
        DoStartKafkaSimpleListenerEvent.publish(context, context);
    }
}
