package com.example.pas22.dto;

import lombok.Getter;
import lombok.Setter;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;

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
