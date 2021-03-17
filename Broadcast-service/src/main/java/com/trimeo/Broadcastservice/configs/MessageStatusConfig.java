package com.trimeo.Broadcastservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("broadcast.message")
public class MessageStatusConfig {

    private Integer activeMessage;
    private Integer activeStatus;
}
