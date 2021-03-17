package com.trimeo.Broadcastservice.services;

import com.trimeo.Broadcastservice.dtos.ChargeServiceDTO;
import com.trimeo.Broadcastservice.interfaces.ChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

    @Override
    public boolean consume(ChargeServiceDTO chargeServiceDTO) {
        return false;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        return null;
    }
}
