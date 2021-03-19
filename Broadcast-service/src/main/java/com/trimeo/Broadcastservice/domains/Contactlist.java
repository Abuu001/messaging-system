package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("contactList")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contactlist {

    @Id
    private Integer _id;

    private Integer clientID;

    private Integer contactID;

    private Integer listID;

    private Integer createdBy;

    private String dateCreated;

    private String dateModified;

    private String description;

    private Integer modifiedBy;
}
