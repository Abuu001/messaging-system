package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("networks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Networks {

    @Id
    private Integer _id;

    private String name;

    private String route;

    private String connector;

    private String country;

    private Integer createdBy;

    private String dateCreated;

    private String dateModified;

    private String description;

    private Integer modifiedBy;
}
