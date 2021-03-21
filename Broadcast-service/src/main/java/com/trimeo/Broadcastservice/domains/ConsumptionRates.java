package com.trimeo.Broadcastservice.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("consumptionRates")
public class ConsumptionRates {

    @Id
    private Integer _id;

    private Integer clientId;

    private Integer rate;

    private Integer createdBy;

    private String dateCreated;

    private Integer modifiedBy;

    private String dateModified;
}
