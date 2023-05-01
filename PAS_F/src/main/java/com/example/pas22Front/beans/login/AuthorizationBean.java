package com.example.pas22Front.beans.login;

import com.example.pas22Front.dto.AuthorizationDto;
import com.example.pas22Front.restClients.AuthorizationRestClient;
import com.example.pas22Front.restClients.exceptions.BadCredentialsException;
import com.example.pas22Front.restClients.exceptions.GeneralBadRequestException;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named("AuthorizationBean")
@ApplicationScoped
@Getter
@Setter
public class AuthorizationBean implements Serializable {

    @Inject
    private Jwt jwt;

    private String login;

    private String password;

    @Inject
    private HttpServletRequest request;

    private AuthorizationRestClient authorizationRestClient;

    private String message = "";
    @PostConstruct
    public void init() {
        authorizationRestClient = new AuthorizationRestClient(jwt.getJwt());
    }

    public String login() throws ServletException {
        try {
            try {
                request.logout();
            } catch (ServletException ignore) {

            }
            String responseJwt = authorizationRestClient.login(new AuthorizationDto(this.login, this.password));
            jwt.setJwt(responseJwt);
            this.message = "Hello "+this.login+"!";

        } catch (BadCredentialsException e) {
            this.message = "Wrong Credentials!";

        }catch (GeneralBadRequestException e) {
            this.message = e.getStatus()+": "+e.getMessage();

        }
        return "";

    }

    public String logoutUser() throws ServletException {
        try {
            authorizationRestClient.logout();
        } catch (Exception e) {
            message = e.getMessage();
        }
        message = login = "";
        jwt.setJwt("");
        request.logout();

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "";
    }
}
