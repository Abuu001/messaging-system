package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;

public interface OutboundService {
    void createOutboundPayload(BroadcastDTO broadcastDTO);
    void persistDataInOutboundDB();
    void pushToOutboundQueue();
}
