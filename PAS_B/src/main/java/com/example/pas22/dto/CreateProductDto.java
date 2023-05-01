package com.example.pas22.dto;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateProductDto {

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @PositiveOrZero
    private float price;

    @JsonbCreator
    public CreateProductDto(@JsonbProperty("name") String name, @JsonbProperty("price") float price) {
        this.name = name;
        this.price = price;
    }
}
