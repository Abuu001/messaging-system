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
    private final FileDataReaderImp fileDataReaderImp;

    public FileValidator(FileDataReaderImp fileDataReaderImp) {
        this.fileDataReaderImp = fileDataReaderImp;
    }

    public boolean checkIfFileIsValid(String filePath) throws IOException {

        Path path = new File(filePath).toPath();
        try {
            String mimeType = Files.probeContentType(path);

            if((mimeType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                    (mimeType.equalsIgnoreCase("application/vnd.ms-excel"))   )){

                return fileDataReaderImp.isFileCsvOrExcell("excellFile");
            }else if(mimeType.equalsIgnoreCase("text/csv")){

                return fileDataReaderImp.isFileCsvOrExcell("csvFile");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
