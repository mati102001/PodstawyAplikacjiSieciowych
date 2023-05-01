package com.example.pas22.authotization;

import com.example.pas22.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtGenerator {

    private static final String SECRET = "4j39j48fmu948cx48cu2j9fjf4h9t87t3g473HGufuJ8fFHU";
    private static final long timeout = 9000000L;


    public String generateJWT(String login, String role, String id) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .claim("userId", id)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + timeout))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Jws<Claims> parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(jwt);
    }
}