package com.example.pas22.dto;

import com.example.pas22.model.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class CreateUserDto {
    @NotNull
    @Size(min = 6)
    private String login;
    @NotNull
    @Size(min = 6)
    private String password;

    @JsonbCreator
    public CreateUserDto(@JsonbProperty("login") String login, @JsonbProperty("password") String password) {
        this.login = login;
        this.password = password;
    }
}
