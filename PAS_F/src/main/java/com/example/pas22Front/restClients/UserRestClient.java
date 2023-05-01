package com.example.pas22Front.restClients;

import com.example.pas22Front.dto.ChangePasswordDto;
import com.example.pas22Front.dto.CreateUserDto;
import com.example.pas22Front.model.User;
import com.example.pas22Front.restClients.exceptions.ForbiddenException;
import com.example.pas22Front.restClients.exceptions.GeneralBadRequestException;
import com.example.pas22Front.restClients.exceptions.UnauthorizedException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


public class UserRestClient {

    private String jwt;

    public UserRestClient(String jwt) {
        this.jwt = jwt;
    }

    private WebTarget getTarget() {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/user");
    }

    public List<User> getAllUsers() throws UnauthorizedException, ForbiddenException {
        Response r = getTarget()
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .get();
        if(r.getStatus() == 401){
            throw new UnauthorizedException();
        }
        if(r.getStatus() == 403){
            throw new ForbiddenException();
        }
        return  r.readEntity(new GenericType<List<User>>() {});
    }

    public User getUser(String id) throws ForbiddenException, UnauthorizedException {
        Response r = getTarget().path(id).request()
                .header("Authorization","Bearer "+jwt).get();
        if(r.getStatus() == 401){
            throw new UnauthorizedException();
        }
        if(r.getStatus() == 403){
            throw new ForbiddenException();
        }
        return r.readEntity(User.class);
    }

    public List<User> getUserByLogin(String login){
        Response r = getTarget().path("search/"+login).request()
                .header("Authorization","Bearer "+jwt).get();
        return r.readEntity(new GenericType<List<User>>() {});
    }

    public List<User> getUserByExactLogin(String login){
        Response r = getTarget().path("search/"+login).request()
                .header("Authorization","Bearer "+jwt).get();
        return r.readEntity(new GenericType<List<User>>() {});
    }
    public boolean addUser(CreateUserDto c) throws GeneralBadRequestException {
        Response r = getTarget().path("add")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .post(Entity.json(c));
        if(r.getStatus() >= 400){
            throw new GeneralBadRequestException(r.getStatusInfo().getReasonPhrase(), r.getStatus());
        }
        return r.getStatus() == 200;
    }

    public boolean unlock(String id, String jws) {
        Response r = getTarget().path("unlock/"+id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .header("If-Match", jws)
                .post(Entity.json(id));
        return r.getStatus() == 200;
    }

    public boolean block(String id, String jws) {
        Response r = getTarget().path("block/"+id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .header("If-Match", jws)
                .post(Entity.json(id));
        return r.getStatus() == 200;
    }

    public boolean deleteUser(String id){
        Response r = getTarget().path("delete/"+id).request()
                .header("Authorization","Bearer "+jwt).delete();
        return r.getStatus() == 200;
    }

    public boolean changePassword(String jwt, ChangePasswordDto changePasswordDto, String jws) throws UnauthorizedException, ForbiddenException {
        Response r = getTarget().path("/changePassword")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .header("If-Match", jws)
                .put(Entity.json(changePasswordDto));
        if(r.getStatus() == 401){
            throw new UnauthorizedException();
        }
        if(r.getStatus() == 403){
            throw new ForbiddenException();
        }
        return r.getStatus() == 200;
    }

    public boolean update(String id, User u, String jws){
        Response r = getTarget().path("update/"+id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+jwt)
                .header("If-Match", jws)
                .put(Entity.json(u));
        return r.getStatus() == 200;
    }

    public String getJwsForUser(String id) throws UnauthorizedException, ForbiddenException {
        Response r = getTarget().path(id).request()
                .header("Authorization","Bearer "+jwt).get();
        if(r.getStatus() == 401){
            throw new UnauthorizedException();
        }
        if(r.getStatus() == 403){
            throw new ForbiddenException();
        }
        return (String) r.getHeaders().getFirst("ETag");
    }
}
