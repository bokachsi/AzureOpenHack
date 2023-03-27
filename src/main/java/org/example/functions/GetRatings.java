package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetRatings {
    /**
     GetRatings
     Verb: GET
     Query string or route parameter: userId
     Requirements
     Get the ratings for the user from your database and return the entire JSON payload for the reviews for the user identified by the id.
     Additional route parameters or query string values may be used if necessary.
     Output payload example:
     [
     {
     "id": "79c2779e-dd2e-43e8-803d-ecbebed8972c",
     "userId": "cc20a6fb-a91f-4192-874d-132493685376",
     "productId": "4c25613a-a3c2-4ef3-8e02-9c335eb23204",
     "timestamp": "2018-05-21 21:27:47Z",
     "locationName": "Sample ice cream shop",
     "rating": 5,
     "userNotes": "I love the subtle notes of orange in this ice cream!"
     },
     {
     "id": "8947f7cc-6f4c-49ed-a7aa-62892eac8f31",
     "userId": "cc20a6fb-a91f-4192-874d-132493685376",
     "productId": "e4e7068e-500e-4a00-8be4-630d4594735b",
     "timestamp": "2018-05-20 09:02:30Z",
     "locationName": "Another Sample Shop",
     "rating": 4,
     "userNotes": "I really enjoy this grape ice cream!"
     }
     ]
     */
    @FunctionName("GetRatings")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @CosmosDBInput(name = "databaseOutput",
                    databaseName = "BFYOC",
                    sqlQuery = "select * from Ratings r where r.userId = {userId}",
                    collectionName = "Ratings",
                    connectionStringSetting = "CosmosDB")
            RatingItem[] ratings,
            final ExecutionContext context) {

        // Item list
        context.getLogger().info("Parameters are: " + request.getQueryParameters());
        context.getLogger().info("Number of items from the database is " + (ratings == null ? 0 : ratings.length));

        // Convert and display
        if (ratings == null || ratings.length == 0) {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND)
                    .body("No documents found.")
                    .build();
        }
        else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(ratings)
                    .build();
        }
    }
}
