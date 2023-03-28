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

    @FunctionName("CreateRating2")
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
        try {
            RatingItem.RatingItemBuilder builder = RatingItem.RatingItemBuilder.getInstance();
            builder.withProductId(request.getQueryParameters().get("productId"))
                    .withUserId(request.getQueryParameters().get("userId"))
                    .withUserNotes(request.getQueryParameters().get("userNotes"))
                    .withLocationName(request.getQueryParameters().get("locationName"))
                    .withRating(request.getQueryParameters().get("rating"))
                    .withId("" + Math.abs(new Random().nextInt()));

            String body = "The product name for your product id" + id + "is Starfruit Explosion";

            RatingItem item = builder.build();
            validateRating(item);
            document.setValue(item);
            context.getLogger().info("Document to be saved: " + document);

            return request.createResponseBuilder(HttpStatus.OK).body(item).build();
        } catch (Exception ex) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Failed to save new rating: " + ex.getLocalizedMessage())
                    .build();
        }

    }

    private static boolean validateRating(RatingItem item) {
        if (item == null)
            throw new RuntimeException("Empty rating object");

        if (item.getRating() < 0 || item.getRating() > 5)
            throw new RuntimeException("Invalid rating value. Rating should be in interval [0-5]");

        if (item.getUserId() == null)
            throw  new RuntimeException("Empty userId");

        if (item.getProductId() == null)
            throw  new RuntimeException("Empty productId");

        //TODO check userId is valid

        //TODO check productId is valid

        return true;
    }


}
