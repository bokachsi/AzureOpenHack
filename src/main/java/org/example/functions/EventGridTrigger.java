package org.example.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Event Grid trigger.
 */
public class EventGridTrigger {
    /**
     * This function will be invoked when an event is received from Event Grid.
     */
    @FunctionName("eventGridMonitor")
    public void logEvent(
            @com.microsoft.azure.functions.annotation.EventGridTrigger(
                    name = "event"
            )
            EventSchema event,
            final ExecutionContext context) {

        context.getLogger().info("Event content: ");
        context.getLogger().info("Subject: " + event.subject);
        context.getLogger().info("Time: " + event.eventTime); // automatically converted to Date by the runtime
        context.getLogger().info("Id: " + event.id);
        context.getLogger().info("Data: " + event.data);

    }
}
