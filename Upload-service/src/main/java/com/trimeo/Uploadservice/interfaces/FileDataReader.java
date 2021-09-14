package com.trimeo.Uploadservice.interfaces;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

public interface FileDataReader {

    void readFromExcell(String filePath);

    void readFromCSv(String filePath);

    Workbook getWorkbook(File file);
}
