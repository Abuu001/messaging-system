package com.trimeo.Uploadservice.controller;

import com.trimeo.Uploadservice.repository.UploadRepository;
import com.trimeo.Uploadservice.service.FileDataReaderImp;
import com.trimeo.Uploadservice.validator.FileAvailability;
import com.trimeo.Uploadservice.validator.FileValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

   @Value("${upload.PROPERTY_EXCEL_SOURCE_FILE_PATH}")
    private  String PROPERTY_EXCEL_SOURCE_FILE_PATH;

    @Autowired
    FileDataReaderImp fileDataReaderImp;

    @Autowired
    FileValidator fileValidator;

    @Autowired
    FileAvailability isFileavailable;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UploadRepository uploadRepository;


    @GetMapping("upload/file")
    public String saveFileUploadedData() throws IOException {

         if(isFileavailable.fileExists(PROPERTY_EXCEL_SOURCE_FILE_PATH)){
             fileValidator.checkIfFileIsValid(PROPERTY_EXCEL_SOURCE_FILE_PATH);
             return "File Uploaded Sucessfully 👻👻👻👻";
         }

        return "File not uploaded 🚨🚨🚨🚨🚨";
    }

}
