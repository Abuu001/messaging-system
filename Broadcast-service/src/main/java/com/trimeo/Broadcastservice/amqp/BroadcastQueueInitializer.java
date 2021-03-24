package com.trimeo.Broadcastservice.amqp;

import com.trimeo.Broadcastservice.configs.QueueConfig;
import com.trimeo.Broadcastservice.exceptions.ExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@AllArgsConstructor
public class BroadcastQueueInitializer {

    @NonNull
    private final QueueConfig queueConfig;

    @Bean
    Queue broadcastQueue(){
        log.info("Initializing broadcast queue :::: " + queueConfig.getIn());
        return new Queue(queueConfig.getIn(),true,false,false);
    }

    // special exchange to enable delay/schedule of messages
    @Bean
    CustomExchange delayExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        args.put("type", "x-delayed-message");

        return new CustomExchange(queueConfig.getDelayExchange(),"x-delayed-message",true,
                false,args);
    }

    @Bean
    Binding binding(Queue broadcastQueue, CustomExchange exchange){
        log.info("Binding queue :::: " + broadcastQueue + " to exchange :::: " + exchange );
        return BindingBuilder.bind(broadcastQueue).
                to(exchange).
                with(queueConfig.getRoutingKey()).noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer(
            ConnectionFactory rabbitConnectionFactory,
            ExceptionHandler exceptionHandler,
            Consumer messageListener
    ){
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(rabbitConnectionFactory);
        listenerContainer.setQueueNames(queueConfig.getIn());
        listenerContainer.setMessageListener(messageListener);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listenerContainer.setPrefetchCount(queueConfig.getPrefetch());
        listenerContainer.setConcurrency(queueConfig.getConsumerCount());
        listenerContainer.setErrorHandler(exceptionHandler);
        listenerContainer.setDefaultRequeueRejected(false);
        return listenerContainer;
    }

}
