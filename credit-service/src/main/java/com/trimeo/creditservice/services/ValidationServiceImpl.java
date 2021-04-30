package com.trimeo.creditservice.services;

import com.trimeo.creditservice.domains.Credits;
import com.trimeo.creditservice.interfaces.validationService;
import com.trimeo.creditservice.repositories.ClientCreditRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements validationService {

    @NonNull
    private ClientCreditRepository clientCreditRepository;

    @Override
    public boolean clientHasEnoughCredits(String clientCode) {
        return false;
    }

    @Override
    public boolean clientExists(String clientCode) {

        Optional<Credits> credits = clientCreditRepository.findByClientCode(clientCode);

        if(credits.isPresent()){
            //
        }else{
            log.error("");
        }

        return false;
    }
}
