package com.example.pas22Front.beans.login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named("Jwt")
@SessionScoped
@Getter
@Setter
public class Jwt implements Serializable {
    private String jwt = "";


    public String getParam(String paramName){

        if(Objects.equals(jwt, "")){
            return "";
        }
        Claims parsedToken = Jwts.parser().parseClaimsJwt(jwt.substring(0, jwt.lastIndexOf('.')+1)).getBody();

        return (String) parsedToken.get(paramName);
    }
}
