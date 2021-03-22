package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;

public interface ProcessorService {

    void incomingBroadcastPayload(BroadcastDTO broadcastDTO);
    void chargeBroadcast(BroadcastDTO broadcastDTO);
    void scheduleChargedBroadcast(BroadcastDTO broadcastDTO);
}
