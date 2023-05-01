package com.example.pas22Front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDto {
    @NotNull
    private String newPassword;
    @NotNull
    private String confirmNewPassword;
    @NotNull
    private String oldPassword;
    @NotNull
    private String login;

    @JsonbCreator
    public ChangePasswordDto(@JsonbProperty("newPassword") String newPassword, @JsonbProperty("confirmNewPassword") String confirmNewPassword, @JsonbProperty("oldPassword")  String oldPassword, @JsonbProperty("login") String login) {
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
        this.oldPassword = oldPassword;
        this.login = login;
    }

}
