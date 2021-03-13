package com.trimeo.Broadcastservice.amqp;

import com.trimeo.Broadcastservice.configs.QueueConfig;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class QueueInitializer {

    @NonNull
    private QueueConfig queueConfig;

    @Bean
    Queue broadcastQueue(){
        log.info("Initializing broadcast queue :::: " + queueConfig.getIn());
        return new Queue(queueConfig.getIn(),true,false,false);
    }

    @Bean
    TopicExchange broadcastExchange(){
        log.info("Initializing exchange :::: " +queueConfig.getExchange());
        return new TopicExchange(queueConfig.getExchange(), true, false);
    }

    @Bean
    Binding binding(Queue broadcastQueue, TopicExchange exchange){
        log.info("Binding queue :::: " + broadcastQueue + " to exchange :::: " + exchange);
        return BindingBuilder.bind(broadcastQueue).
                to(exchange).
                with(queueConfig.getRoutingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setPrefetchCount(queueConfig.getPrefetch());
        simpleMessageListenerContainer.setQueues(broadcastQueue());
        simpleMessageListenerContainer.setMessageListener(new Consumer());
        return simpleMessageListenerContainer;
    }
}
