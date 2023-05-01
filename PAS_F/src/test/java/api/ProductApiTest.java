package api;

import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class ProductApiTest {
    String baseURL = "http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/product";
    HttpClient client = HttpClientBuilder.create().build();



    @BeforeEach
    public void init() throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("name", "TestName");
        body.addProperty("price", 10);


    }

    @Test
    public void testStatusCode() throws IOException {
        HttpResponse response =client.execute(new HttpGet(baseURL));
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);
    }

    @Test
    public void testAddingAndGettingProduct() throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("name", "TestName");
        body.addProperty("price", 10);

        HttpPost post = new HttpPost(baseURL+"/add");

        String json = body.toString();
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);
        JsonObject responseBody = getJsonObjectFromResponse(response);



        HttpResponse response2 = client.execute(new HttpGet(baseURL+"/"+responseBody.get("ID").getAsString()));
        int statusCode2 = response2.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode2 , HttpStatus.SC_OK);

        JsonObject response2Body = getJsonObjectFromResponse(response2);

        Assertions.assertEquals(response2Body.get("id").getAsString(), responseBody.get("ID").getAsString());

    }

    @Test
    public void deletingTest() throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("name", "TestName");
        body.addProperty("price", 10);

        HttpPost post = new HttpPost(baseURL+"/add");

        String json = body.toString();
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);
        JsonObject responseBody = getJsonObjectFromResponse(response);

        HttpDelete delete = new HttpDelete(baseURL+"/delete/"+responseBody.get("ID").getAsString());

        HttpResponse response2 = client.execute(delete);
        statusCode = response2.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);


        HttpResponse response3 = client.execute(delete);
        statusCode = response3.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_BAD_REQUEST);
    }


    @Test
    public void updateTest() throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("name", "TestName");
        body.addProperty("price", 10);

        HttpPost post = new HttpPost(baseURL + "/add");

        String json = body.toString();
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode, HttpStatus.SC_OK);
        JsonObject responseBody = getJsonObjectFromResponse(response);


        JsonObject body2 = new JsonObject();
        body2.addProperty("name", "TestNameUpdated");
        body2.addProperty("price", 11);

        HttpPatch patch = new HttpPatch(baseURL+"/update/"+responseBody.get("ID").getAsString());

        String json2 = body2.toString();
        StringEntity entity2 = new StringEntity(json2);
        patch.setEntity(entity2);
        patch.setHeader("Accept", "application/json");
        patch.setHeader("Content-type", "application/json");

        HttpResponse patchResponse = client.execute(patch);
        int patchStatusCode = patchResponse.getStatusLine().getStatusCode();




        HttpGet get = new HttpGet(baseURL+"/"+responseBody.get("ID").getAsString());
        Assertions.assertEquals(patchStatusCode, HttpStatus.SC_OK);
        HttpResponse getResponse = client.execute(get);
        JsonObject getBodyParsed = getJsonObjectFromResponse(getResponse);

        Assertions.assertEquals("TestNameUpdated" ,getBodyParsed.get("name").getAsString());
        Assertions.assertEquals(11 ,getBodyParsed.get("price").getAsFloat());


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
