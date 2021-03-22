package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;

import java.util.Set;

public interface ContactService {
    int fetchNumberContactsInBroadcast(BroadcastDTO broadcastDTO);
    Set<String> fetchContactsForBroadcast(BroadcastDTO broadcastDTO);
}
