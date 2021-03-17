package com.trimeo.Broadcastservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("broadcast")
public class QueueConfig {

    private String in;
    private String out;
    private String exchange;
    private String routingKey;
    private int prefetch;
    private String consumerCount;
}
