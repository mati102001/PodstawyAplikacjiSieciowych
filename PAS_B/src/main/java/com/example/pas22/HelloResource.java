package com.example.pas22;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/")
public class HelloResource {

  @GET
  @Path("/hello-world")
  public Response HelloResource() {
    return Response.ok().build();
    }



}