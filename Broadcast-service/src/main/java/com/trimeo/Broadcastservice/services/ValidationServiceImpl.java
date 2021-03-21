package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.configs.MessageStatusConfig;
import com.trimeo.Broadcastservice.domains.Broadcast;
import com.trimeo.Broadcastservice.domains.Shortcodes;
import com.trimeo.Broadcastservice.dtos.BroadcastDTO;
import com.trimeo.Broadcastservice.interfaces.ValidationService;
import com.trimeo.Broadcastservice.repositories.BroadcastRepository;
import com.trimeo.Broadcastservice.repositories.ShortcodesRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    @NonNull
    private BroadcastRepository broadcastRepository;

    @NonNull
    private MessageStatusConfig messageStatusConfig;

    @NonNull
    private ShortcodesRepository shortcodesRepository;

    @Override
    public boolean validateBroadcastPayload(@Valid BroadcastDTO broadcastDTO) {

        Optional<Broadcast> broadcast = broadcastRepository.findById(broadcastDTO.getBroadcastID());

        if(broadcast.isPresent()){
            // check if message is active : not cancelled
            if(broadcast.get().getActive() == messageStatusConfig.getActiveMessage()) {
                // check that send time hasn't passed
                if(Timestamp.valueOf(broadcast.get().getExpiryTime()).getTime() >= Timestamp.valueOf(broadcast.get().getSendTime()).getTime()) {
                    log.info("Message Passed Validation " + broadcast.get().get_id());
                    return true;
                }
            }
        }
        log.error(":::::: Payload for Broadcast message_id " +broadcastDTO.getBroadcastID()+ " Failed validation :::::::");
        return false;
    }

    @Override
    public boolean shortCodeActiveAndExist(String shortCode) {

        Optional<Shortcodes> shortcodes = shortcodesRepository.findByName(shortCode);

        if(shortcodes.isPresent()){
            return shortcodes.get().getActive() == messageStatusConfig.getActiveMessage();
        }
        log.error(":::::: Shortcode " + shortCode + " Failed validation ::::::: ");
        return false;
    }
}
