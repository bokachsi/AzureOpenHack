package org.example.functions;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

/**
 * Azure Functions with HTTP Trigger.
 */

public class HttpTriggerJava {
    /**
     CreateRating
     Verb: POST
     Input payload example:
     {
     "userId": "cc20a6fb-a91f-4192-874d-132493685376",
     "productId": "4c25613a-a3c2-4ef3-8e02-9c335eb23204",
     "locationName": "Sample ice cream shop",
     "rating": 5,
     "userNotes": "I love the subtle notes of orange in this ice cream!"
     }
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

        // Parse query parameter
        String query = request.getQueryParameters().get("productId");
        String productId = request.getBody().orElse(query);

        query = request.getQueryParameters().get("userId");
        String userId = request.getBody().orElse(query);

        query = request.getQueryParameters().get("locationName");
        String locationName = request.getBody().orElse(query);

        query = request.getQueryParameters().get("rating");
        String rating = request.getBody().orElse(query);

        query = request.getQueryParameters().get("userNotes");
        String notes = request.getBody().orElse(query);

        // Generate random ID
        final int id = Math.abs(new Random().nextInt());

        String body = "The product name for your product id" + query + "is Starfruit Explosion";

        RatingItem ri = new RatingItem();

        ri.setId(id+"");
        ri.setProductId(productId);
        ri.setUserId(userId);

        try {
            ri.setRating(Integer.parseInt(rating));
        } catch (Exception ignore) {}

        ri.setLocationName(locationName);
        ri.setUserNotes(notes);
        ri.setTimestamp(new Date());

        document.setValue(ri);
        context.getLogger().info("Document to be saved: " + document);


        if (productId == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("A new message Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(body).build();
        }

    }



}
