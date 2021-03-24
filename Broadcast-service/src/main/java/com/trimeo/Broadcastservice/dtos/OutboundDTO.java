package com.trimeo.Broadcastservice.dtos;

import com.trimeo.Broadcastservice.domains.MessageTypes;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
public class OutboundDTO {

    @Transient
    public static final String SEQUENCE_NAME = "outbound_sequence";

    @Id
    private Integer _Id;

    private String shortCode;

    private String destAddr;

    private String clientCode;

    private String message;

    private String networkId;

    private String expiryTime;

    private String sendTime;

    private MessageTypes messageType = MessageTypes.BROADCAST;

    private int numberOfSends;

    private String connector;

}
