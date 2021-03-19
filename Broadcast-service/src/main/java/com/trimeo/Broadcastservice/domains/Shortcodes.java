package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("shortcodes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shortcodes {

    @Id
    private Integer _id;

    private Integer active;

    private Integer clientId;

    private String name;

    private Integer createdBy;

    private String dateCreated;

    private String dateModified;

    private String description;

    private String modifiedBy;

}
