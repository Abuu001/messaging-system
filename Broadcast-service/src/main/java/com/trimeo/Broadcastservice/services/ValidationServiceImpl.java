package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Service
@Validated
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateBroadcast(@Valid BroadcastDTO broadcastDTO) {
        return false;
    }

    @Override
    public boolean notExpired(@Valid BroadcastDTO broadcastDTO) {
        return false;
    }

    @Override
    public boolean shortCodeActiveAndExist(String shortCode) {
        return false;
    }

    @Override
    public boolean checkIfMessageActive(@Valid BroadcastDTO broadcastDTO) {
        return false;
    }
}
