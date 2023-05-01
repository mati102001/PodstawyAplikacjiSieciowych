package com.example.pas22.api;

import com.example.pas22.dto.CreateRentDto;
import com.example.pas22.dto.RentDto;
import com.example.pas22.dto.RentDtoMapper;
import com.example.pas22.managers.UserManager;
import com.example.pas22.managers.ProductManager;
import com.example.pas22.managers.RentManager;
import com.example.pas22.model.Rent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.annotation.security.RolesAllowed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequestScoped
@Path("/rent")
public class RentApi {

    @Inject
    private RentManager rentManager;
    @Inject
    private UserManager userManager;
    @Inject
    private ProductManager productManager;
    @Inject
    private RentDtoMapper rentDtoMapper;

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/")
    public Response getRents() {
        List<RentDto> allDtos = rentDtoMapper.rentsToDtos(rentManager.getAllRents());
        return Response.ok().entity(allDtos).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN","MANAGER", "CUSTOMER"})
    @Path("/myRents")
    public Response getMyRents() {
        if(httpRequest.getHeader("Authorization") == null){
            return Response.status(401).entity("You must be logged in").build();
        }
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Claims parsedToken = Jwts.parser().parseClaimsJwt(token.substring(0, token.lastIndexOf('.')+1)).getBody();

        List<RentDto> allDtos = rentDtoMapper.rentsToDtos(rentManager.getAllRentByCustomer(UUID.fromString((String) parsedToken.get("userId"))));

        return Response.ok().entity(allDtos).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/product/{id}")
    public Response getRentByProduct(@PathParam("id") UUID productId) {
        RentDto dto = rentDtoMapper.rentToDto(rentManager.getRentByProduct(productId));
        return Response.ok().entity(dto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/user/{id}")
    public Response getRentsByCustomer(@PathParam("id")UUID userId) {
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        Claims parsedToken = Jwts.parser().parseClaimsJwt(token.substring(0, token.lastIndexOf('.')+1)).getBody();

        if(parsedToken.get("role") == "CUSTOMER" && parsedToken.get("userId") != userId){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<RentDto> allDtos  = rentDtoMapper.rentsToDtos(rentManager.getAllRentByCustomer(userId));
        return Response.ok().entity(allDtos).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/active/user/{id}")
    public Response getActiveRentsByCustomer(@PathParam("id")UUID userId) {
        List<RentDto> allDtos  = rentDtoMapper.rentsToDtos(rentManager.getActiveRentByCustomer(userId));
        return Response.ok().entity(allDtos).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/past/user/{id}")
    public Response getPastRentsByCustomer(@PathParam("id")UUID userId) {
        List<RentDto> allDtos  = rentDtoMapper.rentsToDtos(rentManager.getPastRentByCustomer(userId));
        return Response.ok().entity(allDtos).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/active")
    public Response getAllActive() {
        List<RentDto> allDtos  = rentDtoMapper.rentsToDtos(rentManager.getAllActive());
        return Response.ok().entity(allDtos).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/add")
    public Response addRent(@Valid CreateRentDto rent) {
        Rent r = rentManager.rentProduct(rentDtoMapper.toRent(rent).getCustomer(), rentDtoMapper.toRent(rent).getProduct());
        if(r==null)
            return Response.status(400).build();
        return Response.ok().entity(r).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/endRent/{id}")
    public Response endRent(@PathParam("id")UUID userId) {
        //UUID rentId = ;
        boolean res = rentManager.endRent(userId);
        if(!res) return Response.status(400).build();
        return Response.ok().entity(rentManager.getRent(userId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/endRent/date")
    public Response endRentWithDate(String body) {
        Gson gson = new Gson();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        JsonObject bodyParsed = gson.fromJson(body, JsonObject.class);
        UUID rentId = UUID.fromString(bodyParsed.get("rent_id").getAsString());
        Date date = new Date();
        try {
            date = formatter.parse(bodyParsed.get("date").getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rentManager.endRent(rentId, date);

        return Response.ok().entity(rentManager.getRent(rentId)).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER"})
    @Path("/{id}")
    public Response getRent(@PathParam("id") UUID rentId){
        Rent r = this.rentManager.getRent(rentId);
        String json = new Gson().toJson(r);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
