package com.trimeo.Broadcastservice.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("outbound")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outbound {

    private Integer _id;

    private String clientCode;

    private String connector;

    private String destination;

    private String expiryTime;

    private String message;

    private String networkId;

    private String sendTime;

    private String shortCode;

    private Integer numberOfSends;
}
