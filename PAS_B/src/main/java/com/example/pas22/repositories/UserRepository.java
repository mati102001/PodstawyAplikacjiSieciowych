package com.example.pas22.repositories;

import com.example.pas22.model.Product;
import com.example.pas22.model.User;
import com.example.pas22.model.UserType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
@ApplicationScoped
public class UserRepository extends Repository<User> {
    @PostConstruct
    public void init(){
        this.add(new User("73f70b44-1aa7-4d7f-abfb-fa459f3594fc", "Pryncypalarz", "Str0ngPa$$word", UserType.ADMIN, false, false));
        this.add(new User("73f70b44-1aa7-4d7f-abfb-fa459f359423", "user12", "123456", UserType.CUSTOMER, false, false));
    }
    @Override
    public boolean add(User obj) {
        if (obj == null) return false;

        for (User user : this.getAll()) {
            if (user.getLogin().equals(obj.getLogin())) return false;
        }
        return super.add(obj);
    }

    @Override
    public boolean update(UUID id, User user) {
        for (User userFromList : this.getAll()) {
            if(userFromList.getId().equals(id)) {
                userFromList.setOnline(user.isOnline());
                userFromList.setLogin(user.getLogin());
                userFromList.setPassword(user.getPassword());
                userFromList.setType(user.getType());
                return true;
            }
        }
        return false;
    }

    public User findUserByExactLogin(String login, String password) {
        return getAll().stream().filter(user -> (user.getLogin().equals(login) && user.getPassword().equals(password))).toList().get(0);
    }

}
