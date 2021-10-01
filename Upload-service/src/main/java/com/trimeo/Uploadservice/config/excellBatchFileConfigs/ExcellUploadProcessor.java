package com.trimeo.Uploadservice.config.excellBatchFileConfigs;

import com.trimeo.Uploadservice.domains.UploadMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

public class ExcellUploadProcessor implements ItemProcessor<UploadMessage, UploadMessage> {

    @Override
    public UploadMessage process(UploadMessage uploadMessage) throws Exception {

        var message = UploadMessage.builder()
                .id(uploadMessage.getId())
                .noOfSends(uploadMessage.getNoOfSends())
                .messageType(uploadMessage.getMessageType())
                .destination(uploadMessage.getDestination())
                .message(uploadMessage.getMessage())
                .sourceAddress(uploadMessage.getSourceAddress())
                .sendTime(uploadMessage.getSendTime())
                .build();

        return message;
    }
}
