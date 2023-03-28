package org.example.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class ServiceBusTrigger {
    /**
     * This function will be invoked when a new message is received at the Service Bus Queue.
     */
    @FunctionName("ServiceBusTrigger")
    public void run(
            @ServiceBusQueueTrigger(name = "message", queueName = "testqueue", connection = "ServiceBus") String message,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Service Bus Queue trigger function executed.");
        context.getLogger().info(message);
    }
}
