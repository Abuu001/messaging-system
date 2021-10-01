package com.trimeo.Uploadservice.controller;

import com.trimeo.Uploadservice.amqp.BatchConfigProperties;
import com.trimeo.Uploadservice.service.FileDataReaderImp;
import com.trimeo.Uploadservice.validator.FileAvailability;
import com.trimeo.Uploadservice.validator.FileValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class FileUploadController {

 /*  @Value("${upload.batch-files-config.PROPERTY_EXCEL_SOURCE_FILE_PATH}")
    private  String PROPERTY_EXCEL_SOURCE_FILE_PATH;*/
    @Autowired
    private BatchConfigProperties batchConfigProperties;

    @Autowired
    private FileDataReaderImp fileDataReaderImp;

    @Autowired
    private  FileValidator fileValidator;

    @Autowired
    private FileAvailability isFileavailable;

    @GetMapping("upload/file")
    public String saveFileUploadedData() throws IOException {

         if(isFileavailable.fileExists(batchConfigProperties.getPROPERTY_SOURCE_FILE_PATH())){
             fileValidator.checkIfFileIsValid(batchConfigProperties.getPROPERTY_SOURCE_FILE_PATH());
             fileDataReaderImp.saveFileDetails(batchConfigProperties.getPROPERTY_SOURCE_FILE_PATH());
             fileDataReaderImp.saveUploadDataToQueue();
             return "File Uploaded Sucessfully 👻👻👻👻";
         }

        return "File not uploaded 🚨🚨🚨🚨🚨";
    }

}
