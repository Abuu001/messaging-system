package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.domains.MessageTypes;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.dtos.OutboundDTO;
import com.trimeo.Broadcastservice.interfaces.OutboundService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboundServiceImpl implements OutboundService {

    @NonNull
    private final AmqpTemplate amqpTemplate;

    @NonNull
    private final ContactServiceImpl contactService;

    @Override
    public void createOutboundPayload(BroadcastDTO broadcastDTO) {
        log.info(":::: Create Payload to send to outbound queue for each contact :::::");
        Set<String> contactSet = contactService.fetchContactsForBroadcast(broadcastDTO);

        OutboundDTO outboundDTO = new OutboundDTO();

        for(String var : contactSet){

            outboundDTO.set_Id(broadcastDTO.getBroadcastID());
            outboundDTO.setShortCode(broadcastDTO.getSourceAddress());
            outboundDTO.setDestAddr(var);
            outboundDTO.setClientCode(broadcastDTO.getClientCode());
            outboundDTO.setMessage(broadcastDTO.getMessage());
            outboundDTO.setNetworkId("1"); //todo: remove hard coded vals
            outboundDTO.setExpiryTime(broadcastDTO.getExpiryTime());
            outboundDTO.setMessageType(MessageTypes.BROADCAST);
            outboundDTO.setNumberOfSends(1);
            outboundDTO.setConnector("sdp"); //todo: remove hard coded vals

        }
    }

    @Override
    @SneakyThrows
    public void persistDataInOutboundDB() {

    }

    @Override
    @SneakyThrows
    public void pushToOutboundQueue() {

    }
}
