package com.example.pas22.managers;

import com.example.pas22.model.User;
import com.example.pas22.model.Product;
import com.example.pas22.model.Rent;
import com.example.pas22.repositories.ProductRepository;
import com.example.pas22.repositories.RentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class RentManager {

    @Inject
    private RentRepository rentRepository;
    @Inject
    private ProductRepository productRepository;

    private Logger logger = Logger.getLogger("RentBean");

    public Rent rentProduct(User user, Product product){
        if(product.isRented() || user.isBlocked()){
            return null;
        }

//        for(Rent r : rentRepository.getAll()){
//            if((r.getCustomer().getId() == user.getId()) && (r.getEndDate().getTime() == 0)){
//                return null;
//            }
//        }

        Rent rent = new Rent(UUID.randomUUID() ,user ,product, new Date(), new Date(0));
        rentRepository.add(rent);
        product.setRented(true);
        return rent;

    }

    public boolean endRent(UUID id){

        Rent r = rentRepository.get(id);
        if(r.getEndDate().getTime() != 0 && r.getEndDate().getTime() != 0){
            return false;
        }
        r.getProduct().setRented(false);
        r.setEndDate(new Date());
        Product copy = new Product(UUID.randomUUID().toString(), r.getProduct().getName(), r.getProduct().getPrice(), true, true);
        productRepository.add(copy);
        r.setProduct(copy);

        rentRepository.update(id, r);
        return true;
    }

    public boolean endRent(UUID id, Date date){
        Rent r = rentRepository.get(id);
        if(r.getEndDate().getTime() != 0 && r.getEndDate().getTime() != 0){
            return false;
        }
        r.getProduct().setRented(false);
        r.setEndDate(date);
        Product copy = new Product(UUID.randomUUID().toString(), r.getProduct().getName().concat(""), r.getProduct().getPrice() + 0, true, true);
        productRepository.add(copy);
        r.setProduct(copy);

        rentRepository.update(id, r);
        return true;
    }

    public List<Rent> getAllRents(){
        return rentRepository.getAll();
    }

    public Rent getRentByProduct(UUID id){
        List<Rent> allRents = rentRepository.getAll();
        for(Rent r : allRents){
            if(r.getProduct().getId() == id){
                return r;
            }
        }
        return null;
    }

    public List<Rent> getAllRentByCustomer(UUID id){
        try {
            FileHandler fh = new FileHandler("/run/media/bartek/D/bartek/Documents/studia/pas/PAS2/logfile.log", true);
            logger.addHandler(fh);
        } catch (Exception ignored){

        }
        List<Rent> all = new ArrayList<Rent>(rentRepository.getAll());
        all.removeIf(r -> !r.getCustomer().getId().equals(id));
        return all;
    }


    public List<Rent> getActiveRentByCustomer(UUID id){
        List<Rent> all = new ArrayList<Rent>(rentRepository.getAll());
        all.removeIf(r -> !r.getCustomer().getId().equals(id));
        all.removeIf(r -> r.getEndDate().getTime() != 0);
        return all;
    }

    public List<Rent> getPastRentByCustomer(UUID id){
        List<Rent> all = new ArrayList<Rent>(rentRepository.getAll());
        all.removeIf(r -> !r.getCustomer().getId().equals(id));
        all.removeIf(r -> r.getEndDate().getTime() == 0);
        return all;
    }

    public List<Rent> getAllActive(){
        List<Rent> all = new ArrayList<Rent>(rentRepository.getAll());
        all.removeIf(r -> r.getEndDate().getTime() != 0);
        return all;
    }
    public Rent getRent(UUID id) {
        return rentRepository.get(id);
    }
}
