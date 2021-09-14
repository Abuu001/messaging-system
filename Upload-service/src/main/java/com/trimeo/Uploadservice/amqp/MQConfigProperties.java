package com.trimeo.Uploadservice.amqp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("upload.rabbit")
public class MQConfigProperties {

    private  String inbound_upload_queue;
    private  String inbound_upload_exchange;
    private  String inbound_upload_routing_key;

    private String outbound_upload_queue;
    private String outbound_upload_exchange;
    private String outbound_upload_routing_key;

}
