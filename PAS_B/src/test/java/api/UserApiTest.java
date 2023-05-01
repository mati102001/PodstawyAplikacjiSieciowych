package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UserApiTest {

    HttpClient client = HttpClientBuilder.create().build();
    String baseURL = "http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/customer";

    @Test
    public void testStatusCode() throws IOException {
        HttpResponse response =client.execute(new HttpGet(baseURL));
        int statusCode = response.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode , HttpStatus.SC_OK);
    }
    @Test
    public void addingAndGettingCustomerTest() throws IOException {

        String login = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        JsonObject body = new JsonObject();
        body.addProperty("login", login);
        body.addProperty("password", password);

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
        System.out.println(responseBody);


        HttpResponse response2 = client.execute(new HttpGet(baseURL+"/"+responseBody.get("ID").getAsString()));
        int statusCode2 = response2.getStatusLine().getStatusCode();
        Assertions.assertEquals(statusCode2 , HttpStatus.SC_OK);

        JsonObject response2Body = getJsonObjectFromResponse(response2);

        Assertions.assertEquals(response2Body.get("id").getAsString(), responseBody.get("ID").getAsString());
    }
    @Test
    public void deletingCustomerTest() throws IOException {

        String login = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        JsonObject body = new JsonObject();
        body.addProperty("login", login);
        body.addProperty("password", password);

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
    public void changeCustomerPasswordTest() throws IOException {
        String login = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        JsonObject body = new JsonObject();
        body.addProperty("login", login);
        body.addProperty("password", password);

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
        body2.addProperty("password", "NewPassword");

        HttpPatch patch = new HttpPatch(baseURL+"/changePassword/"+responseBody.get("ID").getAsString());

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

        Assertions.assertEquals("NewPassword" ,getBodyParsed.get("password").getAsString());
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
