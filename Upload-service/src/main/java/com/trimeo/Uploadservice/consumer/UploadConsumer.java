package com.trimeo.Uploadservice.consumer;

import com.trimeo.Uploadservice.domains.InboundUploadMessage;
import com.trimeo.Uploadservice.domains.OutboundPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UploadConsumer {

   /* @Value("${upload.rabbit.upload_queue}")
    private  final String _UPLOAD_QUEUE;*/

     @RabbitListener(queues = "INBOUNDUPLOAD.QUEUE")
    public void consumerInboundUploadMessageQueue(List<InboundUploadMessage> queuePayload){

        log.info("::::::::::::::::::::::: 🚀🚀🚀 UPLOAD-INBOUND-PAYLOAD received succseefully 🚀🚀🚀::::::::::::::::::::::");
        log.info(String.valueOf(queuePayload)+ "\n \n");
    }

    @RabbitListener(queues = "OUTBOUNDUPLOAD.QUEUE")
    public void consumerOutboundUploadMessageQueue(List<OutboundPayload> queuePayload){
        log.info("::::::::::::::::::::::: 🤪🤪🤪 OUTBOUND-PAYLOAD received succseefully 🤪🤪🤪::::::::::::::::::::::");
        log.info(String.valueOf(queuePayload)+ "\n \n");
    }
}
