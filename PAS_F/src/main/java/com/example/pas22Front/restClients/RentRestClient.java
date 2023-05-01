package com.example.pas22Front.restClients;

import com.example.pas22Front.dto.CreateRentDto;
import com.example.pas22Front.dto.RentDto;
import com.example.pas22Front.model.User;
import com.example.pas22Front.model.Product;
import com.example.pas22Front.model.Rent;
import com.example.pas22Front.restClients.exceptions.ForbiddenException;
import com.example.pas22Front.restClients.exceptions.UnauthorizedException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RentRestClient {

    private String jwt;
    private ProductRestClient productRestClient;
    private UserRestClient userRestClient ;
    public RentRestClient(String jwt) {
        this.jwt = jwt;
        productRestClient = new ProductRestClient(jwt);
        userRestClient = new UserRestClient(jwt);
    }




    private WebTarget getTarget() {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/rent");
    }

    private javax.ws.rs.client.Invocation.Builder getRequest(String path) {
        Client client = ClientBuilder.newClient();
        if(jwt != ""){
            return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/rent").path(path).request(MediaType.APPLICATION_JSON)
                    .header("Authorization","Bearer "+ jwt);
        }else{
            return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/rent").path(path).request(MediaType.APPLICATION_JSON);
        }

    }

    public List<Rent> getRents() throws UnauthorizedException, ForbiddenException {
        Response r = getRequest("")
                .get();
        if(r.getStatus() == 401){
            throw new UnauthorizedException();
        }
        if(r.getStatus() == 403){
            throw new ForbiddenException();
        }
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {});

        return this.RentDtotoRentList(allRentsDto);

    }

    public Rent getRent(String id) throws ForbiddenException, UnauthorizedException {
        Response r = getRequest(id).get();
        return this.toRent(r.readEntity(RentDto.class));
    }

    public List<Rent> getRentsByProduct(String id) throws ForbiddenException, UnauthorizedException {
        Response r = getRequest("product/"+id).get();
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {});
        return this.RentDtotoRentList(allRentsDto);

    }

    public List<Rent> getRentsByUser(String id) throws ForbiddenException, UnauthorizedException {
        Response r = getRequest("user/"+id).get();
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {
        });
        return this.RentDtotoRentList(allRentsDto);
    }
    public List<Rent> getMyRents(String jwt) throws ForbiddenException, UnauthorizedException {
        Response r = getRequest("myRents/").get();
        if (r.getStatus() == 401){
            return null;
        }
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {
        });
        return this.RentDtotoRentList(allRentsDto);
    }
    public List<Rent> getActiveRentsByUser(String id) throws ForbiddenException, UnauthorizedException {
        Response r = getRequest("active/user/"+id).get();
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {});
        return this.RentDtotoRentList(allRentsDto);
    }

    public List<Rent> getPastRentsByUser(String id) throws ForbiddenException, UnauthorizedException {
        Response r = getRequest("past/user/"+id).get();
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {});
        return this.RentDtotoRentList(allRentsDto);
    }

    public List<Rent> getAllActive(String id) throws ForbiddenException, UnauthorizedException {
        Response r =getRequest("active/"+id).get();
        List<RentDto> allRentsDto = r.readEntity(new GenericType<List<RentDto>>() {});
        return this.RentDtotoRentList(allRentsDto);
    }

    public void endRent(String id){
        Response r = getRequest("endRent/"+id).post(null);

    }

    public void addRent(CreateRentDto rent){
        getRequest("add")
                .post(Entity.json(rent));
    }

    public Rent toRent(RentDto rentDto) throws ForbiddenException, UnauthorizedException {
        User user = userRestClient.getUser(rentDto.getCustomerId().toString());
        Product product = productRestClient.getProduct(rentDto.getProductId().toString());

        return new Rent(rentDto.getId(), user, product, rentDto.getStartDate(), rentDto.getEndDate());
    }
    public Rent toRent(CreateRentDto createRentDto) throws ForbiddenException, UnauthorizedException {
        User user = userRestClient.getUser(createRentDto.getCustomerId().toString());
        Product product = productRestClient.getProduct(createRentDto.getProductId().toString());

        return new Rent(UUID.randomUUID() ,user, product, new Date(), new Date(0));
    }

    public List<Rent> RentDtotoRentList(List<RentDto> rentDtos) throws ForbiddenException, UnauthorizedException {

        List<Rent> allRents = new ArrayList<Rent>();

        for (RentDto rentDto:
                rentDtos) {
            allRents.add(this.toRent(rentDto));
        }

        return allRents;
    }

    public List<Rent> CreateRentDtotoRentList(List<CreateRentDto> rentDtos) throws ForbiddenException, UnauthorizedException {

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
    public List<RentDto> rentsToDtos(List<Rent> allRents) {
        List<RentDto> rentDtos = new ArrayList<RentDto>();

        for (Rent r :
                allRents) {
            rentDtos.add(this.rentToDto(r));
        }

        return rentDtos;
    }

}
