package com.example.pas22Front.dto;

import lombok.Getter;
import lombok.Setter;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

@Getter
@Setter
public class AuthorizationDto {

    @JsonbProperty
    private String login;

    @JsonbProperty
    private String password;

    @JsonbCreator
    public AuthorizationDto(@JsonbProperty("login") String login,@JsonbProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

}
