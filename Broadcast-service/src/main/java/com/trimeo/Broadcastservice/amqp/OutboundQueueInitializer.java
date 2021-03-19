package com.trimeo.Broadcastservice.amqp;

import com.trimeo.Broadcastservice.configs.QueueConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OutboundQueueInitializer {

    @NonNull
    private QueueConfig queueConfig;

    @Bean
    Queue outboundQueue(){
        log.info("::::: Initializing outbound queue ::::: " +queueConfig.getOut());
        return new Queue(queueConfig.getOut(),true,false,false);
    }

    @Bean
    TopicExchange outboundExchange(){
        log.info(" :::: Initializing topic exchange :: "+queueConfig.getOutExchange()+" :: for outbound queue :::: ");
        return new TopicExchange(queueConfig.getOutExchange(),true,false);
    }

    @Bean
    Binding outBinding(Queue outboundQueue, TopicExchange exchange){
        log.info("::: Creating binding to outbound queue ::: with key ::: " + queueConfig.getOutKey());
        return BindingBuilder.bind(outboundQueue).to(exchange).with(queueConfig.getOutKey());
    }
}
