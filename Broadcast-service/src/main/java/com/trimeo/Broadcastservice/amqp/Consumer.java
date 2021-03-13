package com.trimeo.Broadcastservice.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class Consumer implements ChannelAwareMessageListener {

    @NonNull
    private final ObjectMapper mapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        BroadcastDTO broadCastPayload = mapper.readValue(message.getBody(), BroadcastDTO.class);

        log.info("Consuming Message - " + broadCastPayload);
    }
}
