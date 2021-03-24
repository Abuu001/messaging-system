package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.amqp.Publisher;
import com.trimeo.Broadcastservice.configs.QueueConfig;
import com.trimeo.Broadcastservice.domains.MessageTypes;
import com.trimeo.Broadcastservice.domains.Outbound;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.dtos.OutboundDTO;
import com.trimeo.Broadcastservice.interfaces.OutboundService;
import com.trimeo.Broadcastservice.mongo_sequences.GenerateOutSequence;
import com.trimeo.Broadcastservice.repositories.OutboundRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboundServiceImpl implements OutboundService {

    @NonNull
    private final ContactServiceImpl contactService;

    @NonNull
    private final OutboundRepository outboundRepository;

    @NonNull
    private final GenerateOutSequence generateOutSequence;

    @NonNull
    private final Publisher publisher;

    @NonNull
    private final QueueConfig queueConfig;

    @Override
    public void createOutboundPayload(BroadcastDTO broadcastDTO) {
        log.info(":::: Create Payload to send to outbound queue for each contact :::::");
        Set<String> contactSet = contactService.fetchContactsForBroadcast(broadcastDTO);

        OutboundDTO outboundDTO = new OutboundDTO();

        for(String msisdn : contactSet){

            outboundDTO.set_Id(generateOutSequence.generateSequence(OutboundDTO.SEQUENCE_NAME));
            outboundDTO.setShortCode(broadcastDTO.getSourceAddress());
            outboundDTO.setDestAddr(msisdn);
            outboundDTO.setClientCode(broadcastDTO.getClientCode());
            outboundDTO.setMessage(broadcastDTO.getMessage());
            outboundDTO.setNetworkId("1"); //todo: remove hard coded vals
            outboundDTO.setSendTime(broadcastDTO.getSendTime());
            outboundDTO.setExpiryTime(broadcastDTO.getExpiryTime());
            outboundDTO.setMessageType(MessageTypes.BROADCAST);
            outboundDTO.setNumberOfSends(1);
            outboundDTO.setConnector("sdp"); //todo: remove hard coded vals

            CompletableFuture<Outbound> outboundFuture = persistDataInOutboundDB(outboundDTO);

            outboundFuture.thenAccept( o -> {
                pushToOutboundQueue(outboundDTO);
            });

        }
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Outbound> persistDataInOutboundDB( OutboundDTO outboundDTO ) {

        Outbound outbound = new Outbound();
        outbound.set_id(outboundDTO.get_Id());
        outbound.setConnector(outboundDTO.getConnector());
        outbound.setClientCode(outboundDTO.getClientCode());
        outbound.setDestination(outboundDTO.getDestAddr());
        outbound.setExpiryTime(outboundDTO.getExpiryTime());
        outbound.setMessage(outboundDTO.getMessage());
        outbound.setNetworkId(outboundDTO.getNetworkId());
        outbound.setSendTime(outboundDTO.getSendTime());
        outbound.setShortCode(outboundDTO.getShortCode());
        outbound.setNumberOfSends(1);

        outboundRepository.save(outbound);

        return CompletableFuture.completedFuture(outbound);
    }

    @Override
    @SneakyThrows
    public void pushToOutboundQueue(OutboundDTO outboundDTO) {
        log.info("Publishing BroadcastID : " + outboundDTO.get_Id() + " to Queue ");
        publisher.publishToQueue(outboundDTO,queueConfig.getOutExchange(), queueConfig.getOutKey());
    }
}
