package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.OutboundService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@RequiredArgsConstructor
public class OutboundServiceImpl implements OutboundService {

    @NonNull
    private final AmqpTemplate amqpTemplate;

    @Override
    public void createOutboundPayload(BroadcastDTO broadcastDTO) {

    }

    @Override
    @Async("threadPoolTaskExecutor")
    @SneakyThrows
    public void persistDataInOutboundDB() {

    }

    @Override
    @Async("threadPoolTaskExecutor")
    @SneakyThrows
    public void pushToOutboundQueue() {

    }
}
