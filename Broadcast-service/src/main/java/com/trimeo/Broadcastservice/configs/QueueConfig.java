package com.trimeo.Broadcastservice.configs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties("broadcast")
public class QueueConfig {

    private String in;
    private String out;
    private String exchange;
    private String routingKey;
    private int prefetch;
}
