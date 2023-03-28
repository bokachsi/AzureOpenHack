package org.example.functions;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class CreateRating {
    /**
     * This function listens at endpoint "/api/HttpTriggerJava". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTriggerJava
     * 2. curl {your host}/api/HttpTriggerJava?name=HTTP%20Query
     */

    @FunctionName("CreateRating")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<RatingItem>> request,
            @CosmosDBOutput(
                    name = "databaseOutput",
                    databaseName = "BFYOC",
                    collectionName = "Ratings",
                    connectionStringSetting = "CosmosDB")
            OutputBinding<RatingItem> document,
            final ExecutionContext context) {

        // Parse query parameter
        RatingItem ri = request.getBody().orElse(null);

        ri.id = UUID.randomUUID().toString();
        ri.timestamp = new Date();
        document.setValue(ri);

        if (!validate(ri)) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Validatior error").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).body(ri).build();
    }

    private boolean validate(RatingItem ri) {
        if (ri.rating < 0 || ri.rating > 5) {
            return false;
        }

        return validateUser(ri) && validateProduct(ri);

    }

    private static boolean validateUser(RatingItem ri) {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpRequest request = null;
        try {
            request = requestFactory.buildGetRequest(
                    new GenericUrl("https://serverlessohapi.azurewebsites.net/api/GetUser?userId=" + ri.userId));
            String rawResponse = request.execute().parseAsString();
            if (!rawResponse.contains("userName")) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static boolean validateProduct(RatingItem ri) {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpRequest request = null;
        try {
            request = requestFactory.buildGetRequest(
                    new GenericUrl("https://serverlessohapi.azurewebsites.net/api/GetProduct?productId=" + ri.productId));
            String rawResponse = request.execute().parseAsString();
            if (!rawResponse.contains("productName")) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
