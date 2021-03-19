package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("contacts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contacts {

    @Id
    private Integer _id;

    private String firstName;

    private String lastName;

    private String msisdn;

    private Integer networkID;

    private String email;

    private Integer createdBy;

    private String dateCreated;

    private String dateModified;

    private String description;

    private Integer modifiedBy;
}
