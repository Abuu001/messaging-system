package com.trimeo.Transactionalservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("transactional.queue")
public class QueueConfig {

    private String in;
    private String out;
    private String outExchange;
    private String routingKey;
    private int prefetch;
    private String consumerCount;
    private String outKey;

}
