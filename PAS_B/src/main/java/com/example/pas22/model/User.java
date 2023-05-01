package com.example.pas22.model;

import com.example.pas22.dao.ModelDao;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

public class User implements ModelDao, Serializable {

    @NotNull
    final private UUID id;

    @NotNull
    @Size(min = 6)
    private String login;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @Enumerated
    private UserType type;

    @NotNull
    private boolean online;

    @NotNull
    private boolean blocked;

    @JsonbCreator
    public User(@JsonbProperty("id") String id, @JsonbProperty("login") String login, @JsonbProperty("password") String password, @JsonbProperty("type") UserType type, @JsonbProperty("blocked") boolean blocked, @JsonbProperty("online") boolean online) {
        this.id = UUID.fromString(id);
        this.login = login;
        this.password = password;
        this.type = type;
        this.blocked = blocked;
        this.online = online;

    }

    @Override
    public UUID getId() { return this.id; }

    public String getLogin() { return login; }

    public void setLogin(String login) {
        if (login.equals("")) return;

        this.login = login;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public UserType getType() {
        return this.type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isBlocked() { return blocked; }

    public void setBlocked(boolean blocked) { this.blocked = blocked; }
}
