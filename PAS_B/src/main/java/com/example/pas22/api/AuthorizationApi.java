package com.example.pas22.api;

import com.example.pas22.authotization.AuthorizationIdentityStore;
import com.example.pas22.authotization.JwtGenerator;
import com.example.pas22.dto.AuthorizationDto;
import com.example.pas22.managers.UserManager;
import com.example.pas22.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.annotation.security.RolesAllowed;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@RequestScoped
@Path("/authorization")
public class AuthorizationApi {

    @Inject
    private AuthorizationIdentityStore authorizationIdentityStore;
    @Inject
    private UserManager userManager;
    @Inject
    private JwtGenerator jwtGenerator;
    @Context
    private HttpServletRequest httpRequest;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GUEST"})
    @Path("/login")
    public Response login(AuthorizationDto authorizationDto){
        try {
            UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential(authorizationDto.getLogin(), authorizationDto.getPassword());
            CredentialValidationResult credentialValidationResult = authorizationIdentityStore.validate(usernamePasswordCredential);
            String userId = userManager.findByExactLogin(authorizationDto.getLogin()).getId().toString();
            if (credentialValidationResult.getStatus().equals(CredentialValidationResult.Status.VALID)) {
                String jwt = jwtGenerator.generateJWT(authorizationDto.getLogin(), credentialValidationResult.getCallerGroups().iterator().next(), userId);
                userManager.getCustomer(UUID.fromString(userId)).setOnline(true);
                return Response.ok().entity(jwt).build();
            }
        } catch (IndexOutOfBoundsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/logout")
    public Response logout(AuthorizationDto authorizationDto){
        try {
            userManager.findByExactLogin(httpRequest.getRemoteUser()).setOnline(false);
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    }
    @GET
    @Path("/")
    public Response checkYourPrivileges(AuthorizationDto authorizationDto){
        return Response.status(Response.Status.UNAUTHORIZED).entity(httpRequest.isUserInRole("GUEST")).build();

    }
}
