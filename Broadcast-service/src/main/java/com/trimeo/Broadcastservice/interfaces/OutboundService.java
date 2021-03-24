package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.dtos.OutboundDTO;

import java.util.concurrent.CompletableFuture;

public interface OutboundService {
    void createOutboundPayload(BroadcastDTO broadcastDTO);
    CompletableFuture<OutboundDTO> persistDataInOutboundDB(OutboundDTO outboundDTO);
    void pushToOutboundQueue(OutboundDTO outboundDTO);
}
