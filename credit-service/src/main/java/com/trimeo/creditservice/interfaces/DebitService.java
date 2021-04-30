package com.trimeo.creditservice.interfaces;

import com.trimeo.creditservice.web.dtos.CreditsDTO;
import org.springframework.http.ResponseEntity;

public interface DebitService {
    ResponseEntity<Object> consumeCredits(CreditsDTO creditsDTO);
}
