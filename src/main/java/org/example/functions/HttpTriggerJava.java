package org.example.functions;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.util.Random;

/**
 * Azure Functions with HTTP Trigger.
 */

public class HttpTriggerJava {
    /**
     * CreateRating
     * Verb: POST
     * Input payload example:
     * {
     * "userId": "cc20a6fb-a91f-4192-874d-132493685376",
     * "productId": "4c25613a-a3c2-4ef3-8e02-9c335eb23204",
     * "locationName": "Sample ice cream shop",
     * "rating": 5,
     * "userNotes": "I love the subtle notes of orange in this ice cream!"
     * }
     */

    @FunctionName("CreateRating")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @CosmosDBOutput(
                    name = "databaseOutput",
                    databaseName = "BFYOC",
                    collectionName = "Ratings",
                    connectionStringSetting = "CosmosDB")
            OutputBinding<RatingItem> document,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Generate random ID
        final int id = Math.abs(new Random().nextInt());

        // Parse query parameter
        RatingItem.RatingItemBuilder builder = RatingItem.RatingItemBuilder.getInstance();
        builder.withProductId(request.getQueryParameters().get("productId"))
                .withUserId(request.getQueryParameters().get("userId"))
                .withUserNotes(request.getQueryParameters().get("userNotes"))
                .withLocationName(request.getQueryParameters().get("locationName"))
                .withRating(request.getQueryParameters().get("rating"))
                .withId("" + Math.abs(new Random().nextInt()));

        String body = "The product name for your product id" + id + "is Starfruit Explosion";
        document.setValue(builder.build());
        context.getLogger().info("Document to be saved: " + document);

        return request.createResponseBuilder(HttpStatus.OK).body(body).build();


    }


}
