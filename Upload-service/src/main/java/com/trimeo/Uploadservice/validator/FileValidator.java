package com.trimeo.Uploadservice.validator;

import com.trimeo.Uploadservice.service.FileDataReaderImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileValidator {

    @Autowired
    FileDataReaderImp fileDataReaderImp;

    public boolean checkIfFileIsValid(String filePath) throws IOException {

        Path path = new File(filePath).toPath();
        try {
            String mimeType = Files.probeContentType(path);

            if((mimeType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                    (mimeType.equalsIgnoreCase("application/vnd.ms-excel"))   )){

               fileDataReaderImp.readFromExcell(filePath);
                return true;
            }else if(mimeType.equalsIgnoreCase("text/csv")){

              fileDataReaderImp.readFromCSv(filePath);
              return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
