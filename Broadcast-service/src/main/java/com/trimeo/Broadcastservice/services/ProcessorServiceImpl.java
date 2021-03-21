package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ProcessorService;
import com.trimeo.Broadcastservice.repositories.ContactlistRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {

    @NonNull
    private final ValidationServiceImpl validationService;

    @NonNull
    private final ContactlistRepository contactlistRepository;

    @Override
    public void incomingBroadcastPayload(BroadcastDTO broadcastDTO) {

        if(validationService.validateBroadcastPayload(broadcastDTO) &&
        validationService.shortCodeActiveAndExist(broadcastDTO.getSourceAddress())){

            if(broadcastDTO.isSend()){
                //TODO: Call service incharge of messaging sending
            }else{
                chargeBroadcast(broadcastDTO);
            }
        }
    }

    @Override
    public int fetchNumberContactsInBroadcast(BroadcastDTO broadcastDTO) {
        return fetchContactsForBroadcast(broadcastDTO).size();
    }

    @Override
    public Set<String> fetchContactsForBroadcast(BroadcastDTO broadcastDTO) {

        ArrayList<Integer> listIds = new ArrayList<>();

        for(int i = 0; i < broadcastDTO.getListIDs().split(",").length; i++){
            listIds.add(Integer.valueOf(broadcastDTO.getListIDs().split(",")[i]));
        }

        return contactlistRepository.findContactId(listIds);
    }

    @Override
    public void chargeBroadcast(BroadcastDTO broadcastDTO) {
        log.info("wahala ::::: " + fetchNumberContactsInBroadcast(broadcastDTO));
        // TODO: Get message length * number of recipients * chargeRate
        // TODO: Send to creditService for charging

    }

    @Override
    public void scheduleChargedBroadcast() {
        // TODO: Call publisher to publish message to delay exchange ttl(time to send message)
    }

}
