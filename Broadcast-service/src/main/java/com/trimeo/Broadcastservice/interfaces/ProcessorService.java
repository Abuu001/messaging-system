package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;

import java.util.Set;

public interface ProcessorService {

    void incomingBroadcastPayload(BroadcastDTO broadcastDTO);
    int fetchNumberContactsInBroadcast(Set<String> list);
    Set<String> fetchContactsForBroadcast(BroadcastDTO broadcastDTO);
    void chargeBroadcast(BroadcastDTO broadcastDTO);
    void scheduleChargedBroadcast();
}
