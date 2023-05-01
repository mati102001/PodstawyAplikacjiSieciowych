package com.example.pas22.model;

import com.example.pas22.dao.ModelDao;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

public class Product implements ModelDao, Serializable {

    @NotNull
    final private UUID id;

    @NotNull
    @Size(min = 6)
    private String name;

    @NotNull
    @PositiveOrZero
    private float price;

    @NotNull
    private boolean rented;

    private boolean archive = false;

    @JsonbCreator
    public Product(@JsonbProperty("id") String id, @JsonbProperty("name") String name, @JsonbProperty("price") float price, @JsonbProperty("rented") boolean rented, @JsonbProperty("archive") boolean archive) {
        this.name = name;
        this.price = price;
        this.id = UUID.fromString(id);
        this.rented = rented;
        this.archive = archive;
    }

    @Override
    public UUID getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
    public void setArchive(boolean archive) {this.archive = archive;}
    public boolean isArchive() {return this.archive;}
}
