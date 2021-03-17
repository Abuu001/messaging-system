package com.trimeo.Broadcastservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeServiceDTO {

    @NotNull(message = "clientCode cannot be null")
    @NotBlank(message = "clientCode must be provided")
    private String clientCode;

    @Positive
    @NotNull(message = "broadcastID must be provided")
    private Integer broadcastID;

    @Positive
    @NotNull(message = "specify credits to consume")
    private double creditToConsume;
}
