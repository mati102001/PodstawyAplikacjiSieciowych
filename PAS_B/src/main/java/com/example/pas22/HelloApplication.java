package com.example.pas22;

import com.example.pas22.managers.ProductManager;
import com.example.pas22.model.Product;
import com.google.gson.Gson;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationPath("/apiv1")
@DeclareRoles({"ADMIN", "MANAGER", "CUSTOMER", "GUEST"})
@RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER", "GUEST"})
public class HelloApplication extends Application {




}