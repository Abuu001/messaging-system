package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ProcessorService;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {

    @NonNull
    private final ValidationServiceImpl validationService;

    @Override
    public void incomingBroadcastPayload(BroadcastDTO broadcastDTO) {

        if(validationService.validateBroadcastPayload(broadcastDTO) &&
        validationService.shortCodeActiveAndExist(broadcastDTO.getSourceAddress())){
            //
        }
    }

    @Override
    public int fetchNumberContactsInBroadcast(Set<String> list) {
        return 0;
    }

    @Override
    public Set<String> fetchContactsForBroadcast(BroadcastDTO broadcastDTO) {
        return null;
    }

    @Override
    public boolean chargeBroadcast(BroadcastDTO broadcastDTO) {
        return false;
    }

    @Override
    public void scheduleChargedBroadcast() {

    }

}
