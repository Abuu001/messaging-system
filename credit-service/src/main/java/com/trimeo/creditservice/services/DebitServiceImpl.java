package com.trimeo.creditservice.services;

import com.trimeo.creditservice.interfaces.DebitService;
import com.trimeo.creditservice.web.dtos.CreditsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DebitServiceImpl implements DebitService {

    @Override
    public ResponseEntity<Object> consumeCredits(CreditsDTO creditsDTO) {
        return null;
    }
}
