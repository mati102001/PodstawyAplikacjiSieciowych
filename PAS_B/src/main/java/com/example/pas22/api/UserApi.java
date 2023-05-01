package com.example.pas22.api;

import com.example.pas22.authotization.JwsGenerator;
import com.example.pas22.dto.ChangePasswordDto;
import com.example.pas22.dto.CreateUserDto;
import com.example.pas22.managers.UserManager;
import com.example.pas22.model.User;
import com.example.pas22.model.UserType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nimbusds.jose.JOSEException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Path("/user")
public class UserApi {

    @Inject
    private UserManager userManager;
    @Context
    private HttpServletRequest httpRequest;
    @Inject
    JwsGenerator jwsGenerator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/")
    public Response getAllUsers(){
        List<User> all = this.userManager.getAll();
        String json = new Gson().toJson(all);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") UUID userId){
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Claims parsedToken = Jwts.parser().parseClaimsJwt(token.substring(0, token.lastIndexOf('.')+1)).getBody();

        if(parsedToken.get("role").equals("CUSTOMER")  && !parsedToken.get("userId").equals(userId.toString())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        User p = this.userManager.getCustomer(userId);
        String json = new Gson().toJson(p);
        try {
            return Response.ok(json, MediaType.APPLICATION_JSON).header("ETag", getJwsFromUser(userId)).build();
        } catch (JOSEException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/search/{login}")
    public Response getCustomerByLogin(@PathParam("login") String userLogin){
            List<User> c = this.userManager.findByLogin(userLogin);
            return Response.ok(c, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "GUEST"})
    @Path("/add")
    public Response addCustomer(@Valid CreateUserDto user){
        if(user.getLogin() == null || user.getPassword()== null){
            return Response.status(400, "Please enter login and password").build();
        }

        User newUser = this.userManager.add(user.getLogin(), user.getPassword(), UserType.CUSTOMER);
        if(newUser == null){
            return Response.status(400, "Username must be unique").build();
        }
        return Response.ok().entity(newUser).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @Path("/unlock/{id}")
    public Response unlock(@PathParam("id") UUID userId){
        String jsw = httpRequest.getHeader("If-Match");
        User c = userManager.getCustomer(userId);
        userManager.unblockCustomer(userId, jsw);

        return Response.ok().entity(c).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @Path("/block/{id}")
    public Response block(@PathParam("id") UUID userId){
        String jsw = httpRequest.getHeader("If-Match");
        User c = userManager.getCustomer(userId);
        userManager.blockCustomer(userId, jsw);

        return Response.ok().entity(c).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteProduct(@PathParam("id") UUID userId){
        boolean res = userManager.delete(userId);
        if(res){
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @PUT
    @Path("/changePassword")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response changePassword(@Valid ChangePasswordDto changePasswordDto){
        String jsw = httpRequest.getHeader("If-Match");
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Claims parsedToken = Jwts.parser().parseClaimsJwt(token.substring(0, token.lastIndexOf('.')+1)).getBody();

        boolean res = userManager.updatePassword(changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword(), (String) parsedToken.get("sub"), jsw);
        if (changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword()) && res) {
            return Response.ok().build();
        } else {
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @RolesAllowed({"ADMIN"})
    public Response update(@PathParam("id") UUID userId, @Valid User user){
        String jsw = httpRequest.getHeader("If-Match");
        boolean res = userManager.update(userId, user, jsw);
        if(res){
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    public String getJwsFromUser(UUID id) throws NotFoundException, JOSEException {
        User user = userManager.getCustomer(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", user.getId().toString());
        return this.jwsGenerator.generateJws(jsonObject.toString());
    }

}
