package com.trimeo.Uploadservice.publisher;

import com.trimeo.Uploadservice.amqp.MQConfigProperties;
import com.trimeo.Uploadservice.domains.InboundUploadMessage;
import com.trimeo.Uploadservice.domains.OutboundPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UploadPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private MQConfigProperties mqConfigProperties;

    public String setInboundUpload(List<InboundUploadMessage> inboundUploadMessage){

        rabbitTemplate.convertAndSend(mqConfigProperties.getInbound_upload_exchange(),mqConfigProperties.getInbound_upload_routing_key(), inboundUploadMessage);
        log.info(":::::::::::::::::::::::::🤸🤸 UPLOAD-INBOUND-PAYLOAD 🤸🤸::::::::::::::::::::::::");
        log.info("upload inbound payload >> " + inboundUploadMessage);
        return "Payload sent to uploadQueue Sucessfully  🦾🤗🤗🤗";
    }

    public String setOutboundUpload(List<OutboundPayload> outboundUploadMessage){

        rabbitTemplate.convertAndSend(mqConfigProperties.getOutbound_upload_exchange(),mqConfigProperties.getOutbound_upload_routing_key(), outboundUploadMessage);
        log.info("::::::::::::::::::::::::: 💥💥💥 UPLOAD-OUTBOUND-PAYLOAD 💥💥💥 ::::::::::::::::::::::::");
        log.info("upload outbound payload >> " + outboundUploadMessage);

        return "Payload sent to uploadQueue Sucessfully  🦾🤗🤗🤗";
    }
}
