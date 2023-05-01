package com.example.pas22.api;

import com.example.pas22.dto.CreateProductDto;
import com.example.pas22.managers.ProductManager;
import com.example.pas22.model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
@Path("/product")
public class ProductApi {

    @Inject
    private ProductManager productManager;
    @Context
    private HttpServletRequest httpRequest;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER", "GUEST"})
    @Path("/")
    public Response getAllProduct(){
        List<Product> all = this.productManager.getAll();
        String json = new Gson().toJson(all);
        return Response.status(200).entity(json).build();
    }
//
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/visible")
    public Response getAllVisibleProduct(){
        List<Product> all = this.productManager.getAllVisible();
        String json = new Gson().toJson(all);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/add")
    public Response addProduct(@Valid CreateProductDto p){
        Product newProduct = this.productManager.add(p.getName(), p.getPrice());
        return Response.ok().entity(newProduct).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER", "GUEST"})
    @Path("/{id}")
    public Response getProduct(@PathParam("id") UUID productId){
        Product p = this.productManager.getProduct(productId);
        String json = new Gson().toJson(p);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Path("/delete/{id}")
    public Response deleteProduct(@PathParam("id") UUID productId){
        boolean res = productManager.delete(productId);
        if(res){
            return Response.ok().build();
        }
        return Response.status(400).build();
    }
    @PUT
    @Path("/update/{id}")
    @RolesAllowed({"ADMIN", "MANAGER"})
    public Response updateProduct(@PathParam("id") UUID productId, @Valid Product p){
        productManager.update(productId, p);
        return Response.ok().build();
    }
}
