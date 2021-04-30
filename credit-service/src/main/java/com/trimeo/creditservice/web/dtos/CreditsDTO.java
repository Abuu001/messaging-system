package com.trimeo.creditservice.web.dtos;

import com.trimeo.creditservice.domains.CreditOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreditsDTO {

    @NotNull(message = "clientCode cannot be null")
    @NotEmpty(message = "clientCode must be sent on payload")
    private String clientCode;

    @NotBlank(message = "number of credits must be specified")
    @NotNull(message = "number of credits cannot be null")
    @Positive(message = "number of credits cannot be negative")
    private Float numberOfCredits;

    @NotNull(message = "operationType must be specified on payload")
    @NotEmpty(message = "operationType must be specified on payload")
    private CreditOperation operationType = CreditOperation.getDefault();
}
