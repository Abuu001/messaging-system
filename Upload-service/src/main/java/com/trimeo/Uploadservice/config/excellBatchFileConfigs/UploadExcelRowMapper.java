package com.trimeo.Uploadservice.config.excellBatchFileConfigs;

import com.trimeo.Uploadservice.domains.UploadMessage;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import java.time.LocalDateTime;

public class UploadExcelRowMapper  implements RowMapper<UploadMessage> {

    @Override
    public UploadMessage mapRow(RowSet rowSet) throws Exception {

        //TODO: remove dummy data
        int randNumber = RandomUtils.nextInt(1, 11);
        var payload = UploadMessage.builder()
                .id(String.valueOf(rowSet.getCurrentRowIndex()))
                .destination(rowSet.getColumnValue(0))
                .message(rowSet.getColumnValue(1))
                .messageType("UPLOAD")
                .sendTime(LocalDateTime.now())
                .sourceAddress("KCB")
                .noOfSends(randNumber)
                .build();

        return payload;
    }
}
