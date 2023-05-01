package com.example.pas22Front.beans.login;

import com.example.pas22Front.beans.login.Jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@AutoApplySession
@Named("AuthMechanismMVC")
@ApplicationScoped
public class MVCAuthorizationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private Jwt jwt;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) {
        Set<String> roles = new HashSet<>();
        if (jwt.getJwt().equals("")) {
            roles.add("GUEST");
            return httpMessageContext.notifyContainerAboutLogin("GUEST", roles);
        }
        String unsignedToken = jwt.getJwt().substring(0, jwt.getJwt().lastIndexOf('.')+1);
        Claims claims = (Claims) Jwts.parser().parse(unsignedToken).getBody();
        roles.add(claims.get("role", String.class));
        return httpMessageContext.notifyContainerAboutLogin(claims.getSubject(), roles);
    }
}