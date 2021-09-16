package com.trimeo.Uploadservice.validator;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileAvailability {

    public boolean fileExists(String filePath){
        File selectedFile = new File(filePath);
        boolean exists = selectedFile.exists();

        return exists;
    }
}
