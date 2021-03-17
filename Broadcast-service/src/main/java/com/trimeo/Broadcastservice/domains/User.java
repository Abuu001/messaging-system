package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    private String _id;

    private Integer createdBy;

    private String client;

    private String dateCreated;

    private String dateModified;

    private String email;

    private String firstName;

    @Field("lastname")
    private String lastName;

    private String modifiedBy;

    private String password;

    private String phone;

    private String status;

    private String type;
}
