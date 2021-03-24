package com.trimeo.Broadcastservice.amqp;

import com.trimeo.Broadcastservice.configs.QueueConfig;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Publisher {

    @NonNull
    private final AmqpTemplate amqpTemplate;

    @NonNull
    private final QueueConfig queueConfig;

    @Retryable(value = {AmqpIOException.class, AmqpConnectException.class}, maxAttempts = 3,backoff = @Backoff(500))
    @Async("threadPoolTaskExecutor")
    public void sendToDelayExchange(BroadcastDTO broadcastDTO, Long delay){

        try{
            amqpTemplate.convertAndSend(
                    queueConfig.getDelayExchange(),
                    queueConfig.getRoutingKey(),
                    broadcastDTO,
                    message -> {
                        message.getMessageProperties().getHeaders().put("x-delay", delay);
                        return message;
                    }
            );

            log.info("::: Payload sent to delay exchange :::");

        }catch (Exception ex){
            log.error("Exception occurred publishing message ::: " + ex.getMessage());
        }
    }

    @Async("threadPoolTaskExecutor")
    public void publishToQueue(Object object, String exchange, String routingKey){
        try {
            amqpTemplate.convertAndSend(exchange, routingKey, object);
            log.info("Message published to exchange :: "+ exchange+" successfully using routing-key :: " + routingKey);
        }catch (Exception ex){
            log.error("Fatal error occurred during publish to queue ::: " + ex.getMessage());
        }
    }

    @Recover
    public void failBroadcastAfterRetry(AmqpConnectException ex, String queueName){
        log.error("Failed to publish after n retries to queue : " + queueName);
    }
}
