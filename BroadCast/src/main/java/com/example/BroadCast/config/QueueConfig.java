package com.example.BroadCast.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "broadcast")
public class QueueConfig {
    private String in;
    private String out;
}
