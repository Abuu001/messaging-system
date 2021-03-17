package com.trimeo.Broadcastservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("broadcast.time")
public class TimeConfig {

    private String format;
    private String timezone;
}
