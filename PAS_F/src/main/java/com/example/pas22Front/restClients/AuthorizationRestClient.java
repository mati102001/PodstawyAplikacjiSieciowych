package com.example.pas22Front.restClients;

import com.example.pas22Front.dto.AuthorizationDto;
import com.example.pas22Front.restClients.exceptions.BadCredentialsException;
import com.example.pas22Front.restClients.exceptions.GeneralBadRequestException;

import javax.ws.rs.core.HttpHeaders;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthorizationRestClient {

    private String jwt;

    public AuthorizationRestClient(String jwt) {
        this.jwt = jwt;
    }

    private WebTarget getTarget() {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/authorization");
    }
    private javax.ws.rs.client.Invocation.Builder getRequest(String path) {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/authorization").path(path).request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+ jwt);
    }
    public String login(AuthorizationDto authorizationDto) throws BadCredentialsException, GeneralBadRequestException {
        Response r = getRequest("login")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .post(Entity.json(authorizationDto));
        if(r.getStatus() == 401){
            throw new BadCredentialsException();
        }
        if(r.getStatus() >= 400){
            throw new GeneralBadRequestException(r.getStatus());
        }
        return r.readEntity(String.class);

    }

    public String logout() throws BadCredentialsException, GeneralBadRequestException {
        Response r = getRequest("logout")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .post(null);
        if(r.getStatus() == 401){
            throw new BadCredentialsException();
        }
        if(r.getStatus() >= 400){
            throw new GeneralBadRequestException(r.getStatus());
        }
        return r.readEntity(String.class);

    }

}
