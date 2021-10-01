package com.trimeo.Uploadservice.service;

import com.trimeo.Uploadservice.domains.OutboundPayload;
import com.trimeo.Uploadservice.domains.UploadMessage;
import com.trimeo.Uploadservice.publisher.UploadPublisher;
import com.trimeo.Uploadservice.domains.InboundUploadMessage;
import com.trimeo.Uploadservice.interfaces.FileDataReader;
import com.trimeo.Uploadservice.repository.UploadMessageRepository;
import com.trimeo.Uploadservice.repository.UploadRepository;
import com.trimeo.Uploadservice.validator.ActiveUploadValidation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FileDataReaderImp implements FileDataReader {

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private UploadMessageRepository uploadMessageRepository;

    @Autowired
    UploadPublisher uploadPublisher;

    @Autowired
    private ActiveUploadValidation activeUploadValidation;

    @Override
    public Boolean isFileCsvOrExcell(String fileType) {

        if(fileType.equalsIgnoreCase("excellFile")){
            return true;
        }else if(fileType.equalsIgnoreCase("csvFile")){
            return false;
        }

        return false;
    }

    @Override
    public void saveUploadDataToQueue() {

        List<UploadMessage> allUploadMessage = uploadMessageRepository.findAll();

        List<OutboundPayload> allOutboundData = allUploadMessage.stream()
                .map(outboundData -> new OutboundPayload(
                        outboundData.getId(),
                        outboundData.getMessage(),
                        outboundData.getMessageType(),
                        outboundData.getDestination(),
                        outboundData.getSourceAddress(),
                        outboundData.getNoOfSends()
                        )).collect(Collectors.toList());


        // send data to outbound payload queue
        uploadPublisher.setOutboundUpload(allOutboundData);
    }

    @Override
    public void saveFileDetails(String filePath) {

        //TODO: remove dummy data
         String fileName = new File(filePath).getName();
         var fileDetails = InboundUploadMessage.builder()
                 .filename(fileName)
                 .sourceAddress("AIRTEL")
                 .status(1)
                 .build();

         uploadRepository.save(fileDetails);

         //push to payload
        //TODO: send data to inbound payload queue if status==active
        List<InboundUploadMessage> allActiveUploads = uploadRepository.findByStatus(1);
        uploadPublisher.setInboundUpload(allActiveUploads);
      /*  if(activeUploadValidation.isUploadActive(outboundPayload.getStatus())){
            uploadPublisher.setInboundUpload(outBoundPayloadData);
        }*/
    }
}
