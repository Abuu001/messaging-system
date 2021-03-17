package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("broadcast")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Broadcast {

    @Id
    private Integer _id;

    private String clientCode;

    private String dateCreated;

    private String dateModified;

    private String expiryTime;

    private List<Integer> listIDs;

    private String sendTime;

    private Integer active;

    private String sourceAddress;

    private Integer createdBy;

    private String message;

}
