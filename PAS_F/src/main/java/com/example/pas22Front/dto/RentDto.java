package com.example.pas22Front.dto;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class RentDto {

    @NotNull
    final private UUID id;
    @NotNull
    final private UUID customerId;
    @NotNull
    final private UUID productId;
    @NotNull
    final private Date startDate;
    @NotNull
    private Date endDate;

    @JsonbCreator
    public RentDto(@JsonbProperty("id") UUID id, @JsonbProperty("customerId") UUID customerId, @JsonbProperty("productId") UUID productId, @JsonbProperty("startDate") Date startDate, @JsonbProperty("endDate") Date endDate) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;

    }

}
