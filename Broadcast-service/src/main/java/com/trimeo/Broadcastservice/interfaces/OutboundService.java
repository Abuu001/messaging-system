package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.domains.Outbound;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.dtos.OutboundDTO;

import java.util.concurrent.CompletableFuture;

public interface OutboundService {
    void createOutboundPayload(BroadcastDTO broadcastDTO);
    CompletableFuture<Outbound> persistDataInOutboundDB(OutboundDTO outboundDTO);
    void pushToOutboundQueue(OutboundDTO outboundDTO);
}
