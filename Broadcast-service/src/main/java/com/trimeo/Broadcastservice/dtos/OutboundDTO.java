package com.trimeo.Broadcastservice.dtos;

import com.trimeo.Broadcastservice.domains.MessageTypes;
import lombok.Data;

@Data
public class OutboundDTO {

    private Integer _Id;

    private String shortCode;

    private String destAddr;

    private String clientCode;

    private String message;

    private String networkId;

    private String expiryTime;

    private MessageTypes messageType = MessageTypes.BROADCAST;

    private int numberOfSends;

    private String connector;

}
