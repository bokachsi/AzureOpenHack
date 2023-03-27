package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetRating {
    /**
     GetRating
     Verb: GET
     Query string or route parameter: ratingId
     Requirements
     Get the rating from your database and return the entire JSON payload for the review identified by the id
     Additional route parameters or query string values may be used if necessary.
     Output payload example:
     {
     "id": "79c2779e-dd2e-43e8-803d-ecbebed8972c",
     "userId": "cc20a6fb-a91f-4192-874d-132493685376",
     "productId": "4c25613a-a3c2-4ef3-8e02-9c335eb23204",
     "timestamp": "2018-05-21 21:27:47Z",
     "locationName": "Sample ice cream shop",
     "rating": 5,
     "userNotes": "I love the subtle notes of orange in this ice cream!"
     }
     */
    @FunctionName("GetRating")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @CosmosDBInput(name = "databaseOutput",
                    databaseName = "BFYOC",
//                    id = "{Query.id}",
//                    partitionKey = "{Query.productId}",
                    sqlQuery = "select * from Ratings r where r.id = {id}",
                    collectionName = "Ratings",
                    connectionStringSetting = "CosmosDB")
            RatingItem item,
            final ExecutionContext context) {

        // Item list
        context.getLogger().info("Parameters are: " + request.getQueryParameters());
        context.getLogger().info("Item from the database is " + item);

        // Convert and display
        if (item == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Document not found.")
                    .build();
        }
        else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(item)
                    .build();
        }
    }
}
