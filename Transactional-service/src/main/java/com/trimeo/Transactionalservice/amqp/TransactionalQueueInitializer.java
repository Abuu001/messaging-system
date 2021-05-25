package com.trimeo.Transactionalservice.amqp;

import com.trimeo.Transactionalservice.configs.QueueConfig;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TransactionalQueueInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalQueueInitializer.class);

    private final QueueConfig queueConfig;

    @Bean
    Queue TransactionalQueue(){
        LOGGER.info("::::: Initializing incoming queue :::::: " + queueConfig.getIn());
        return new Queue(queueConfig.getIn(),true,false,false);
    }

    @Bean
    Queue outgoingQueue(){
        LOGGER.info("::::: Initializing outgoing queue :::::: " + queueConfig.getOut());
        return new Queue(queueConfig.getOut(),true,false,false);
    }

    @Bean
    TopicExchange outboundExchange(){
        LOGGER.info(":::: Initialising topic exchange :::: " + queueConfig.getOutExchange());
        return new TopicExchange(queueConfig.getOutExchange(),true,false);
    }

    @Bean
    Binding binding(Queue outgoingQueue, TopicExchange exchange){
        LOGGER.info(":::: Initialising binding to Queue ::::: " + queueConfig.getOut() + " ::: with routing key ::: " +queueConfig.getRoutingKey() +
                " ::: to exchange :::" +queueConfig.getOutExchange() );
        return BindingBuilder.bind(outgoingQueue).to(exchange).with(queueConfig.getRoutingKey());
    }


    @Bean
    public SimpleMessageListenerContainer listenerContainer(
            ConnectionFactory rabbitConnectionFactory
    ){
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(rabbitConnectionFactory);
        listenerContainer.setQueueNames(queueConfig.getIn());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listenerContainer.setPrefetchCount(queueConfig.getPrefetch());
        listenerContainer.setConcurrency(queueConfig.getConsumerCount());
        listenerContainer.setDefaultRequeueRejected(false);
        return listenerContainer;
    }

}