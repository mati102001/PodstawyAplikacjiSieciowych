package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

public class RentApiTest {
    String customerLogin = UUID.randomUUID().toString();
    String customerPassword = UUID.randomUUID().toString();
    String customerId;
    String productName = UUID.randomUUID().toString();

    Float productPrice = new Random().nextFloat()*100;
    String productId;

    String rentId;

    String rentURL = "http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/rent";
    String customerURL = "http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/customer";
    String productURL = "http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/product";



    @BeforeEach
    public void init() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        JsonObject createCustomerBody = new JsonObject();
        createCustomerBody.addProperty("login", customerLogin);
        createCustomerBody.addProperty("password", customerPassword);

        HttpPost customerPost = new HttpPost(customerURL+"/add");
        StringEntity customerEntity = new StringEntity(createCustomerBody.toString());
        customerPost.setEntity(customerEntity);
        customerPost.setHeader("Accept", "application/json");
        customerPost.setHeader("Content-type", "application/json");

        HttpResponse customerResponse = client.execute(customerPost);
        JsonObject customerResponseBody = getJsonObjectFromResponse(customerResponse);
        customerId = customerResponseBody.get("ID").getAsString();

        JsonObject createProductBody = new JsonObject();
        createProductBody.addProperty("name", productName);
        createProductBody.addProperty("price", productPrice);

        HttpPost productPost = new HttpPost(productURL+"/add");
        StringEntity productEntity = new StringEntity(createProductBody.toString());
        productPost.setEntity(productEntity);
        productPost.setHeader("Accept", "application/json");
        productPost.setHeader("Content-type", "application/json");

        HttpResponse productResponse = client.execute(productPost);
        JsonObject productResponseBody = getJsonObjectFromResponse(productResponse);
        productId = productResponseBody.get("ID").getAsString();

        client.execute(productPost);

        HttpPost customerUnlock = new HttpPost(customerURL+"/unlock/"+customerId);
        HttpResponse r = client.execute(customerUnlock);
        System.out.println(r.getStatusLine().getStatusCode());
    }
    @AfterEach
    public void cleanUp() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpDelete customerDelete = new HttpDelete(customerURL+"/delete/"+customerId);
        client.execute(customerDelete);
        HttpDelete productDelete = new HttpDelete(productURL+"/delete/"+productId);
        client.execute(productDelete);
    }


    @Test
    public void addingAndGettingRentTest() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        JsonObject body = new JsonObject();
        body.addProperty("product_id", productId);
        body.addProperty("customer_id", customerId);

        HttpPost post = new HttpPost(rentURL+"/add");
        String json = body.toString();
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        System.out.println(post);
        HttpResponse response = client.execute(post);

        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);
        JsonObject responseBody = getJsonObjectFromResponse(response);

        rentId = responseBody.get("ID").getAsString();

        HttpResponse response2 = client.execute(new HttpGet(rentURL+"/"+rentId));
        int statusCode2 = response2.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode2 , HttpStatus.SC_OK);

        JsonObject response2Body = getJsonObjectFromResponse(response2);

        Assertions.assertEquals(response2Body.get("id").getAsString(), rentId);
    }


    @Test
    public void endingRent() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete productDelete = new HttpDelete(productURL+"/delete/"+productId);
        HttpResponse response = client.execute(productDelete);
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);

    }



    public JsonObject getJsonObjectFromResponse(HttpResponse r) throws IOException {
        String responseBody = EntityUtils.toString(r.getEntity(), StandardCharsets.UTF_8);
        return new Gson().fromJson(responseBody, JsonObject.class);
    }
    public JsonArray getJsonArrayFromResponse(HttpResponse r) throws IOException {
        String responseBody = EntityUtils.toString(r.getEntity(), StandardCharsets.UTF_8);
        return new Gson().fromJson(responseBody, JsonArray.class);
    }
}
