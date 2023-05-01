package com.example.pas22.repositories;
import com.example.pas22.model.Product;
import com.example.pas22.model.Rent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class RentRepository extends Repository<Rent> {



    @Override
    public boolean update(UUID id, Rent rent) {
        for (Rent rentFromList : this.getAll()) {
            if (rentFromList.getId().equals(id)) {
                rentFromList.setEndDate(rent.getEndDate());
                return true;
            }
        }
        return false;
    }
}
