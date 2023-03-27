package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
class RatingItem {
    public String id;
    public String productId;

}

public class HttpTriggerJava {
    /**
     * This function listens at endpoint "/api/HttpTriggerJava". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTriggerJava
     * 2. curl {your host}/api/HttpTriggerJava?name=HTTP%20Query
     */

    @FunctionName("HttpTriggerJava")
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
        String name = request.getBody().orElse(query);

        String body = "The product name for your product id" + query + "is Starfruit Explosion";

        RatingItem ri = new RatingItem();
        ri.id = "234234234-java";
        ri.productId = "prd-id2";
        document.setValue(ri);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("A new message Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(body).build();
        }

    }



}
