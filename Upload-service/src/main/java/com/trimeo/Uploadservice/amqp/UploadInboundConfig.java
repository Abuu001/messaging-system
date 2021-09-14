package com.trimeo.Uploadservice.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadInboundConfig {

    @Autowired
    private  MQConfigProperties mqConfigProperties;

    @Bean
    public Queue uploadInboundQueue(){
        return new Queue(mqConfigProperties.getInbound_upload_queue(),true);
    }

    @Bean
    public DirectExchange uploadInboundDirectExchange(){
        return new DirectExchange(mqConfigProperties.getInbound_upload_exchange());
    }

    @Bean
    public Binding uploadInboundBinding(Queue uploadInboundQueue, DirectExchange uploadInboundDirectExchange){
        return BindingBuilder
                .bind(uploadInboundQueue)
                .to(uploadInboundDirectExchange)
                .with(mqConfigProperties.getInbound_upload_routing_key());
    }

    @Bean
    public MessageConverter uploadMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate inboundTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(uploadMessageConverter());
        return template;
    }

}
