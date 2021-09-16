package com.trimeo.Uploadservice.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadOutboundConfig {
    @Autowired
    private  MQConfigProperties mqConfigProperties;

    @Bean
    public Queue uploadOutboundQueue(){
        return new Queue(mqConfigProperties.getOutbound_upload_queue(),true);
    }

    @Bean
    public DirectExchange uploadOutboundDirectExchange(){
        return new DirectExchange(mqConfigProperties.getOutbound_upload_exchange());
    }

    @Bean
    public Binding uploadOutboundBinding(Queue uploadOutboundQueue, DirectExchange uploadOutboundDirectExchange){
        return BindingBuilder
                .bind(uploadOutboundQueue)
                .to(uploadOutboundDirectExchange)
                .with(mqConfigProperties.getOutbound_upload_routing_key());
    }

}
