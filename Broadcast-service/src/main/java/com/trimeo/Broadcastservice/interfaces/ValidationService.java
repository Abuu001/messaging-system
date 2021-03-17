package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;

public interface ValidationService {

    boolean validateBroadcast(BroadcastDTO broadcastDTO);
    boolean notExpired(BroadcastDTO broadcastDTO);
    boolean shortCodeActiveAndExist(String shortCode);
    boolean checkIfMessageActive(BroadcastDTO broadcastDTO);

}
