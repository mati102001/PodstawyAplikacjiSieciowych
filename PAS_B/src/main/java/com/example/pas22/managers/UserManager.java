package com.example.pas22.managers;

import com.example.pas22.model.User;
import com.example.pas22.model.UserType;
import com.example.pas22.authotization.JwsGenerator;

import com.example.pas22.repositories.UserRepository;
import com.nimbusds.jose.JOSEException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stateless
public class UserManager {
    @Inject
    private UserRepository cr;

    @Context
    private SecurityContext securityContext;
    
    private JwsGenerator jwsGenerator = new JwsGenerator();

    public List<User> getAll() { return cr.getAll(); }

    public User add(String login, String password, UserType userType) {
        User user = new User(UUID.randomUUID().toString() ,login, password, UserType.CUSTOMER, true, false);
        boolean res = cr.add(user);
        if (res){
            return user;
        }
        return null;
    }

    public void updateType(UUID id, UserType userType, String jws) {
        User current = cr.get(id);
        current.setType(userType);
        this.update(id, current, jws);
    }

    public boolean updatePassword(String oldPassword, String newPassword, String username, String jws) {
        try {
            jwsGenerator.verify(jws);
            User user = findByExactLogin(username);
            if(user != null && !oldPassword.equals(newPassword)) {
                user.setPassword(newPassword);
                return true;
            }
            if(oldPassword.equals(newPassword)) {
                throw new SecurityException("New password and old password matches");
            }
            return false;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean update(UUID id, @Valid User u, String jws) {
        try {
            jwsGenerator.verify(jws);
            String currentLogin = cr.get(id).getLogin();
            return  cr.update(id, u);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    public void blockCustomer(UUID id, String jws) {
        try {
            jwsGenerator.verify(jws);
            cr.get(id).setBlocked(true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public void unblockCustomer(UUID id, String jws) {
        try {
            jwsGenerator.verify(jws);
            cr.get(id).setBlocked(false);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOnline(UUID id, String jws) {
        try {
            jwsGenerator.verify(jws);
            cr.get(id).setOnline(true);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOffline(UUID id, String jws) {
        try {
            jwsGenerator.verify(jws);
            cr.get(id).setOnline(false);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public User getCustomer(UUID id){
        return cr.get(id);
    }

    public List<User> findByLogin(String loginFilter) {
        ArrayList<User> users = new ArrayList<>();
        for (User userFromList : cr.getAll()) {
            if(userFromList.getLogin().contains(loginFilter)) {
                users.add(userFromList);
            }
        }
        return users;
    }

    public User findByExactLogin(String loginFilter) {
        for (User userFromList : cr.getAll()) {
            if(userFromList.getLogin().equals(loginFilter)) {
                return userFromList;
            }
        }
        return null;
    }

    public boolean delete(UUID customerId) {
        return cr.delete(customerId);
    }
}
