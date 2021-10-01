package com.trimeo.Uploadservice.config.csvBatchFileConfig;

import com.trimeo.Uploadservice.domains.UploadMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

@Slf4j
public class CsvUploadProcessor implements ItemProcessor<UploadMessage, UploadMessage> {


    @Override
    public UploadMessage process(UploadMessage uploadMessage) throws Exception {

        final int SHORT_ID_LENGTH = 6;
        String shortId = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);

        //TODO: remove dummy data
        int randNumber = RandomUtils.nextInt(1, 11);
        var upload =UploadMessage.builder()
                .id(String.valueOf(shortId))
                .destination(uploadMessage.getDestination())
                .message(uploadMessage.getMessage())
                .messageType("UPLOAD")
                .noOfSends(randNumber)
                .sendTime(LocalDateTime.now())
                .sourceAddress("SENDER_TEST")
                .build();

        return upload;
    }
}
