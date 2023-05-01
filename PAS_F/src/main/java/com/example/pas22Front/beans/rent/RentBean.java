package com.example.pas22Front.beans.rent;
import com.example.pas22Front.beans.login.Jwt;
import com.example.pas22Front.beans.roles.Roles;
import com.example.pas22Front.dto.CreateRentDto;
import com.example.pas22Front.model.Rent;
import com.example.pas22Front.model.User;
import com.example.pas22Front.restClients.RentRestClient;
import com.example.pas22Front.restClients.UserRestClient;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
@Named("RentBean")
@SessionScoped
public class RentBean implements Serializable {
    private RentRestClient rentRestClient;
    private List<Rent> allRents;
    private List<Rent> userCurrentRents;
    private List<Rent> userPastRents;

    private String chosenCustomerId = "";
    private String chosenProductId = "";
    private User currentUser = null;
    private FileHandler fh;
    private Logger logger = Logger.getLogger("RentBean");

    private String message = "";
    @Inject
    private Roles roles;
    @Inject
    private Jwt jwt;

    private UserRestClient userRestClient;
    @PostConstruct
    public void init() {
        rentRestClient = new RentRestClient(jwt.getJwt());
        userRestClient = new UserRestClient(jwt.getJwt());
        this.fetchRents();
    }


    public void fetchRents(){
        try{
            if(roles.isUser()){
                if(rentRestClient.getMyRents(this.jwt.getJwt()) != null){
                    allRents = rentRestClient.getMyRents(this.jwt.getJwt());

                }else{
                    allRents = new ArrayList<>();

                }
            }else{
                allRents = rentRestClient.getRents();
            }
        }catch (Exception e){
            this.message = e.toString();
        }






    }
    public void fetchRents(String param){
        try {
            allRents = rentRestClient.getRentsByUser(param);

        }catch (Exception e){
            this.message = e.getMessage();
        }
    }
    public String goToRentCreator(){
        this.chosenCustomerId = "";
        this.chosenProductId = "";
        return "toRentCreator";
    }

    public String addRent(){
        if(roles.isUser()){
            this.chosenCustomerId = jwt.getParam("userId");
        }
        rentRestClient.addRent(new CreateRentDto(UUID.fromString(chosenCustomerId), UUID.fromString(chosenProductId)));
        this.chosenCustomerId = "";
        this.chosenProductId = "";
        return "toRents";
    }

    public void endRent(String rentId){
        rentRestClient.endRent(rentId);
    }

    public String goToUserRents(User u){
        currentUser = u;
        try {
            userCurrentRents = rentRestClient.getActiveRentsByUser(u.getId().toString());
            userPastRents = rentRestClient.getPastRentsByUser(u.getId().toString());
        }catch (Exception e){
            this.message = e.getMessage();
        }

//        fetchRents();
        logger.log(Level.INFO, String.valueOf(userCurrentRents.size()));
        return "UserDetails";
    }

}