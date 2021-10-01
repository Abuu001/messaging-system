package com.trimeo.Uploadservice.amqp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("upload.batch-files-config")
public class BatchConfigProperties {
    private int chunckSize;
    private int uuidLength;

    private String PROPERTY_SOURCE_FILE_PATH;
}
