package com.example.pas22Front.restClients;



import com.example.pas22Front.model.Product;
import com.example.pas22Front.restClients.exceptions.GeneralBadRequestException;

import javax.ws.rs.core.HttpHeaders;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.List;

public class ProductRestClient implements Serializable {

    private String jwt;

    public ProductRestClient(String jwt) {
        this.jwt = jwt;
    }

    private javax.ws.rs.client.Invocation.Builder getRequest(String path) {
        Client client = ClientBuilder.newClient();
        if(jwt != ""){
            return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/product").path(path).request(MediaType.APPLICATION_JSON)
                    .header("Authorization","Bearer "+ jwt);
        }else{
            return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/product").path(path).request(MediaType.APPLICATION_JSON);
        }

    }

    private WebTarget getTarget() {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080/Pas22-1.0-SNAPSHOT/apiv1/product");
    }

    public List<Product> getAllProducts() throws GeneralBadRequestException {
        Response r = getRequest("visible")
                .get();
        if(r.getStatus() >= 400){
            throw new GeneralBadRequestException(r.getStatus());
        }
        return  r.readEntity(new GenericType<List<Product>>() {});
    }
    public void deleteProduct(String id){
        getRequest("delete/"+id)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization","Bearer "+jwt)
                .delete();
    }
    public Product getProduct(String id){
        Response r = getRequest(id)
                .header(HttpHeaders.CONTENT_TYPE, "application/json").get();
        return r.readEntity(Product.class);
    }

    public void updateProduct(Product p) {
            getRequest("update/"+p.getId().toString())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Authorization","Bearer "+jwt)
                .put(Entity.json(p));

    }

    public void addProduct(Product p){
        getRequest("add")
                .header("Authorization","Bearer "+jwt)
                .post(Entity.json(p));
    }
//
//    public List<RoomDto> getAllFreeRooms() {
//        List<RoomDto> freeRooms = new ArrayList();
//        List<RoomDto> allRooms = this.getAllRooms();
//
//        for (RoomDto room : allRooms) {
//            if (rentRestClient.currentRentsByRoomId(room.getId().toString()).isEmpty()) {
//                freeRooms.add(room);
//            }
//        }
//
//        return freeRooms;
//    }
//
//    public void postRoom(RoomDto newRoom) {
//        getTarget()
//                .request(MediaType.APPLICATION_JSON)
//                .post(Entity.json(newRoom));
//    }
//
//    public void deleteRoom(String roomId) {
//        getTarget().path(roomId)
//                .request()
//                .delete();
//    }
//
//    public void updateRoom(RoomDto newRoom) {
//        getTarget().path(newRoom.getId().toString())
//                .request(MediaType.APPLICATION_JSON)
////                .header("If-Match", newCostume.getEtag())
//                .put(Entity.json(newRoom));
//    }
//
//    public RoomDto getRoomById(String id) {
//        return getTarget().path(id)
//                .request(MediaType.APPLICATION_JSON)
//                .get(RoomDto.class);
//    }
}
