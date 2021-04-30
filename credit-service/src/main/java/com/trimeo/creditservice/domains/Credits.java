package com.trimeo.creditservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("credits")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credits {

    @Id
    private Integer _id;

    private Integer clientId;

    private String clientCode;

    private Integer createdBy;

    private String dateCreated;

    private String dateModified;

    private Integer modifiedBy;
}
