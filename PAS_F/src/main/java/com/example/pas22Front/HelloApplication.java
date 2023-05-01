package com.example.pas22Front;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/apiv1")
@DeclareRoles({"ADMIN", "MANAGER", "CUSTOMER", "GUEST"})
@RolesAllowed({"ADMIN", "MANAGER", "CUSTOMER", "GUEST"})
public class HelloApplication extends Application {




}