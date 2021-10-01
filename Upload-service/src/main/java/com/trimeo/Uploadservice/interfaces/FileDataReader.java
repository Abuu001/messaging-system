package com.trimeo.Uploadservice.interfaces;


public interface FileDataReader {

    Boolean isFileCsvOrExcell(String fileType);

    void saveUploadDataToQueue();

    void saveFileDetails(String filePath);
}
