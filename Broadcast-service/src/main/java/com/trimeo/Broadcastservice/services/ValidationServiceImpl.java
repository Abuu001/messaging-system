package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
public class ValidationServiceImpl {

    public boolean validatePayload(@Valid BroadcastDTO message){
        return true;
    }
}
