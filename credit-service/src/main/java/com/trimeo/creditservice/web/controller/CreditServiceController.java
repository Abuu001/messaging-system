package com.trimeo.creditservice.web.controller;

import com.trimeo.creditservice.web.dtos.CreditsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/credits")
@RequiredArgsConstructor
public class CreditServiceController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> fetchClientCredits(@PathVariable String clientCode){
        return new ResponseEntity<>("response", HttpStatus.OK); // todo: implement service to fetch clientcredits
    }

    @PostMapping(value = "allocate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> allocateClientCredits(@Valid @RequestBody CreditsDTO payload){
        return new ResponseEntity<>("test", HttpStatus.OK); // TODO: IMPL service
    }

    @PostMapping(value = "allocate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> consumeCredits(@Valid @RequestBody CreditsDTO payload){
        return new ResponseEntity<>("test", HttpStatus.OK); // TODO: IMPL service
    }
}
