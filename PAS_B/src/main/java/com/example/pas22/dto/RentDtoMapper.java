package com.example.pas22.dto;

import com.example.pas22.managers.UserManager;
import com.example.pas22.managers.ProductManager;
import com.example.pas22.managers.RentManager;
import com.example.pas22.model.User;
import com.example.pas22.model.Product;
import com.example.pas22.model.Rent;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RentDtoMapper {

    @Inject
    private RentManager rentManager;
    @Inject
    private UserManager userManager;
    @Inject
    private ProductManager productManager;

    public Rent toRent(RentDto rentDto)  {
        User user = userManager.getCustomer(rentDto.getCustomerId());
        Product product = productManager.getProduct(rentDto.getProductId());

        return new Rent(rentDto.getId(), user, product, rentDto.getStartDate(), rentDto.getEndDate());
    }
    public Rent toRent(CreateRentDto createRentDto)  {
        User user = userManager.getCustomer(createRentDto.getCustomerId());
        Product product = productManager.getProduct(createRentDto.getProductId());

        return new Rent(UUID.randomUUID() ,user, product, new Date(), null);
    }

    public List<Rent> RentDtotoRentList(List<RentDto> rentDtos){

        List<Rent> allRents = new ArrayList<Rent>();

        for (RentDto rentDto:
                rentDtos) {
            allRents.add(this.toRent(rentDto));
        }

        return allRents;
    }

    public List<Rent> CreateRentDtotoRentList(List<CreateRentDto> rentDtos){

        List<Rent> allRents = new ArrayList<Rent>();

        for (CreateRentDto rentDto:
                rentDtos) {
            allRents.add(this.toRent(rentDto));
        }

        return allRents;
    }

    public RentDto rentToDto(Rent rent){
        return new RentDto(rent.getId(), rent.getCustomer().getId(), rent.getProduct().getId(), rent.getStartDate(), rent.getEndDate());
    }
    public List<RentDto> rentsToDtos(List<Rent> allRents){
        List<RentDto> rentDtos = new ArrayList<RentDto>();

        for (Rent r:
                allRents) {
            rentDtos.add(this.rentToDto(r));
        }

        return rentDtos;
    }
    public RentDtoMapper() {}
}
