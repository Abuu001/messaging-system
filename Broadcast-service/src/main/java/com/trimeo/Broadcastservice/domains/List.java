package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("list")
@JsonIgnoreProperties(ignoreUnknown = true)
public class List {

    @Id
    private Integer id;

    private String name;

    private Integer createdBy;

    private String dateCreated;

    private String dateModified;

    private String description;

    private Integer modifiedBy;
}
