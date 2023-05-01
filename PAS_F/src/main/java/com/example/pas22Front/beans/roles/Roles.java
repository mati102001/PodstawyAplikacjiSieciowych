package com.example.pas22Front.beans.roles;

import com.example.pas22Front.beans.login.Jwt;
import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ws.rs.core.SecurityContext;

@Named("Roles")
@ApplicationScoped
@Getter
public class Roles implements Serializable {

    private String adminName = "ADMIN";
    private String managerName = "MANAGER";
    private String customerName = "CUSTOMER";

    @Inject
    private Jwt jwt;
    @Context
    private SecurityContext securityContext;
    @Context
    private HttpServletRequest httpRequest;
    private String roleFromContext;

    public boolean isAdministrator() {
        return this.getRole().equals("ADMIN");
    }

    public boolean isUser() {
        return this.getRole().equals("CUSTOMER");
    }

    public boolean isManager() {
        return this.getRole().equals("MANAGER");
    }

    public boolean isGuest() {
        return this.getRole().equals("GUEST");
    }

//    public String getRole() {
//        if (!jwt.getParam("role").equals("")) {
//            return jwt.getParam("role");
//        }
//        return "GUEST";
//    }

    public String getRole(){

        for (String roleName: new String[]{"ADMIN", "MANAGER", "CUSTOMER"} ) {
            if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole(roleName))
                return roleName;
        }
        return "GUEST";

    }

}