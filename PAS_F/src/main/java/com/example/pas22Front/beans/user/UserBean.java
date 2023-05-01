package com.example.pas22Front.beans.user;
import com.example.pas22Front.beans.login.Jwt;
import com.example.pas22Front.beans.roles.Roles;
import com.example.pas22Front.dto.ChangePasswordDto;
import com.example.pas22Front.dto.CreateUserDto;
import com.example.pas22Front.model.User;
import com.example.pas22Front.restClients.UserRestClient;
import com.example.pas22Front.restClients.exceptions.ForbiddenException;
import com.example.pas22Front.restClients.exceptions.GeneralBadRequestException;
import com.example.pas22Front.restClients.exceptions.UnauthorizedException;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Named("UserBean")
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private Jwt jwt;

    private UserRestClient userRestClient;
    private List<User> allUsers;
    private User updatedUser;
    private CreateUserDto newUser;
    private String searchPhrase = "";
    private ChangePasswordDto changePasswordDto = new ChangePasswordDto();
    private String message = "";

    @Inject
    private Roles roles;
    private User currentUser = null;
    @PostConstruct
    public void init(){
        userRestClient = new UserRestClient(jwt.getJwt());
//        allUsers = userRestClient.getAllUsers();
        newUser = new CreateUserDto("", "");

    }

    public ChangePasswordDto getPasswordChangeDto() {
        return changePasswordDto;
    }

    public void setPasswordChangeDto(ChangePasswordDto changePasswordDto) {
        this.changePasswordDto = changePasswordDto;
    }

    public String changePassword() {
        String id = jwt.getParam("userId");
        if(!this.changePasswordDto.getNewPassword().equals(this.changePasswordDto.getConfirmNewPassword())){
            message = "Passwords don't match!";
            return "";
        }
        try {
            String jws = userRestClient.getJwsForUser(id);
            boolean res = userRestClient.changePassword(jwt.getJwt(), changePasswordDto, jws);
            message = "password changed successfully";
        } catch (UnauthorizedException e) {
            message = e.getMessage();
        } catch (ForbiddenException e) {
            message = e.getMessage();
        }
        return "";

    }
    public void fetchUsers(){
        try {
            allUsers = userRestClient.getAllUsers();
            this.message = "okay";
        }catch (Exception e){
            this.message = e.getMessage();
        }

    }

    public void resetFilter(){
        searchPhrase = "";
        fetchUsers();
    }

    public void deleteUser(String id){
        userRestClient.deleteUser(id);
        this.fetchUsers();
    }

    public String unlockUser(String id){
        try {
            String jws = userRestClient.getJwsForUser(id);
            userRestClient.unlock(id, jws);
        } catch (UnauthorizedException | ForbiddenException e) {
            throw new RuntimeException(e);
        }
        this.fetchUsers();
        return "toUsers";
    }

    public String blockUser(String id){
        try {
            String jws = userRestClient.getJwsForUser(id);
            userRestClient.block(id, jws);
        } catch (UnauthorizedException | ForbiddenException e) {
            throw new RuntimeException(e);
        }
        this.fetchUsers();
        return "toUsers";
    }

    public String goToUserEdit(String id) throws ForbiddenException, UnauthorizedException {
        this.updatedUser = userRestClient.getUser(id);
        return "toUserEditPage";
    }
    public String updateUser(){
        try {
            String jws = userRestClient.getJwsForUser(updatedUser.getId().toString());
            userRestClient.update(updatedUser.getId().toString(), updatedUser, jws);
        } catch (UnauthorizedException e) {
            throw new RuntimeException(e);
        } catch (ForbiddenException e) {
            throw new RuntimeException(e);
        }
        this.fetchUsers();
        return "toUsers";
    }

    public void filter(){
        if (searchPhrase.length() >= 1) {
            allUsers = userRestClient.getUserByLogin(searchPhrase);
        }else{
            this.fetchUsers();
        }
    }

    public String goToUserCreator(){
        newUser = new CreateUserDto("", "");
        return "toUserCreatorPage";
    }

    public String addUser(){
        try {
            userRestClient.addUser(newUser);
            message = "User created successfully";
        } catch (GeneralBadRequestException e) {
            message = e.getMessage();

        }
        newUser = new CreateUserDto("", "");
        return "";
    }


}
