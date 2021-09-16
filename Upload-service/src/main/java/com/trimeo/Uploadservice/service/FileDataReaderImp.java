package com.trimeo.Uploadservice.service;

import com.opencsv.CSVReader;
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
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

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
    public void readFromExcell(String filePath) {
        try {
            File file = new File(filePath);

            Workbook workbook = getWorkbook(file);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Sheet> sheets = workbook.sheetIterator();

            while(sheets.hasNext()) {
                Sheet sh = sheets.next();

                Iterator<Row> iterator = sh.iterator();
                iterator.next();
                while(iterator.hasNext()) {
                    Row row = iterator.next();
                    Iterator<Cell> cellIterator = row.iterator();

                    InboundUploadMessage uploadedFile = new InboundUploadMessage();
                    UploadMessage uploadedData = new UploadMessage();
                    OutboundPayload outboundPayload = new OutboundPayload();

                    uploadedFile.setFilename(file.getName());
                    uploadedData.setSendTime(LocalDateTime.now());
                    outboundPayload.setSendTime(LocalDateTime.now().toString());

                    List<OutboundPayload> outBoundPayloadData =List.of(outboundPayload);
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        String cellValue = dataFormatter.formatCellValue(cell);

                        if(row.getCell(0).getCellType()== CellType.NUMERIC){
                            uploadedData.setDestination(String.valueOf(row.getCell(0).getNumericCellValue()));
                            outboundPayload.setDestination(String.valueOf(row.getCell(0).getNumericCellValue()));
                        }
                        if(row.getCell(1).getCellType()== CellType.STRING){
                            uploadedData.setMessage(row.getCell(1).getStringCellValue());
                            outboundPayload.setMessage(row.getCell(1).getStringCellValue());
                        }
                        if(row.getCell(2).getCellType()== CellType.STRING){
                            uploadedData.setMessageType(row.getCell(2).getStringCellValue());
                            outboundPayload.setMessageType(row.getCell(2).getStringCellValue());
                        }
                        if(row.getCell(3).getCellType()== CellType.STRING){
                            uploadedFile.setSourceAddress(row.getCell(3).getStringCellValue());
                            uploadedData.setSourceAddress(row.getCell(3).getStringCellValue());
                            outboundPayload.setSourceAddress(row.getCell(3).getStringCellValue());
                        }

                        if(row.getCell(4).getCellType()== CellType.NUMERIC){
                            uploadedData.setNoOfSends((int) row.getCell(4).getNumericCellValue());
                        }

                        if(row.getCell(5).getCellType()== CellType.NUMERIC){
                            uploadedFile.setStatus((int) row.getCell(5).getNumericCellValue());
                            outboundPayload.setStatus((int) row.getCell(5).getNumericCellValue());
                        }
                        List<InboundUploadMessage> allFiles = List.of(uploadedFile);
                        uploadRepository.saveAll(allFiles);

                        List<UploadMessage> allFileData = List.of(uploadedData);
                        uploadMessageRepository.saveAll(allFileData);
                    }

                    // send data to inbound payload queue
                    List<InboundUploadMessage> allFileUploadsFromDB = uploadRepository.findAll();
                    uploadPublisher.setInboundUpload(allFileUploadsFromDB);

                    // send data to outbound payload queue
                    if(activeUploadValidation.isUploadActive(outboundPayload.getStatus())){
                        uploadPublisher.setOutboundUpload(outBoundPayloadData);
                    }

                }
            }
            workbook.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFromCSv(String filePath) {

        String fileName = new File(filePath).getName();

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));

            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                InboundUploadMessage uploadedFile = new InboundUploadMessage();
                UploadMessage uploadedData = new UploadMessage();
                OutboundPayload outboundPayload = new OutboundPayload();

                List<OutboundPayload> outBoundPayloadData =List.of(outboundPayload);

                if (nextLine != null) {

                    uploadedFile.setFilename(fileName);
                    uploadedFile.setSourceAddress(nextLine[3]);
                    uploadedFile.setStatus(Integer.parseInt(nextLine[5]));

                    uploadedData.setSourceAddress(nextLine[3]);
                    uploadedData.setMessage(nextLine[1]);
                    uploadedData.setNoOfSends(Integer.parseInt(nextLine[4]));
                    uploadedData.setMessageType(nextLine[2]);
                    uploadedData.setDestination(nextLine[0]);
                    uploadedData.setSendTime(LocalDateTime.now());

                    outboundPayload.setSourceAddress(nextLine[3]);
                    outboundPayload.setMessageType(nextLine[2]);
                    outboundPayload.setStatus(Integer.parseInt(nextLine[5]));
                    outboundPayload.setMessage(nextLine[1]);
                    outboundPayload.setDestination(nextLine[0]);
                    outboundPayload.setSendTime(LocalDateTime.now().toString());

                    List<InboundUploadMessage> allFiles = List.of(uploadedFile);
                    uploadRepository.saveAll(allFiles);

                    List<UploadMessage> allFileData = List.of(uploadedData);
                    uploadMessageRepository.saveAll(allFileData);

                    // send data to inbound payload queue
                    List<InboundUploadMessage> allFileUploadsFromDB = uploadRepository.findAll();
                    uploadPublisher.setInboundUpload(allFileUploadsFromDB);

                    // send data to outbound payload queue if status==active
                    if(activeUploadValidation.isUploadActive(outboundPayload.getStatus())){
                        uploadPublisher.setOutboundUpload(outBoundPayloadData);
                    }
                }
            }

            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Workbook getWorkbook(File file) {
        Workbook workbook = null;
        String extension = FileNameUtils.getExtension(String.valueOf(file));

        try{
            if(extension.equalsIgnoreCase("xlsx")){
                workbook = new XSSFWorkbook(file);
            }else if(extension.equalsIgnoreCase("xls")){
                // TODO : should read excell files for .xls extension
                workbook = new HSSFWorkbook(POIFSFileSystem.create(file));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  workbook;
    }
}
