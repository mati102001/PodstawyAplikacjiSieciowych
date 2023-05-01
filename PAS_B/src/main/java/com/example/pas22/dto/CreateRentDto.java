package com.example.pas22.dto;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreateRentDto {
    @NotNull
    final private UUID id = UUID.randomUUID();

    @NotNull
    final private UUID customerId;
    @NotNull
    final private UUID productId;

    @JsonbCreator
    public CreateRentDto(@JsonbProperty("customerId") UUID customerId, @JsonbProperty("productId") UUID productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

}
