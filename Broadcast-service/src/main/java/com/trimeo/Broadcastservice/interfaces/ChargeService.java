package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.ChargeServiceDTO;
import org.springframework.http.client.ClientHttpRequestFactory;

public interface ChargeService {

    boolean consume(ChargeServiceDTO chargeServiceDTO);
    ClientHttpRequestFactory getClientHttpRequestFactory();
}
