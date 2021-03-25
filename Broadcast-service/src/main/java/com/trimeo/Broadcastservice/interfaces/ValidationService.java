package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;

import javax.validation.Valid;

public interface ValidationService {

    boolean validateBroadcastPayload(@Valid BroadcastDTO broadcastDTO);
    boolean shortCodeActiveAndExist(String shortCode);
    boolean messageNotPastExpiryDateTime(String dateTime);
}
