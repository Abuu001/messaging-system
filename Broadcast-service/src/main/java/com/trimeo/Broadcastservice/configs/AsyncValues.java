package com.trimeo.Broadcastservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("broadcast.async")
public class AsyncValues {

    private Integer threadPoolSize;
    private Integer maxThreadPoolSize;

}
