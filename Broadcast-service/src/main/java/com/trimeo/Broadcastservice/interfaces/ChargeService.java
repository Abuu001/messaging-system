package com.trimeo.Broadcastservice.interfaces;

import com.trimeo.Broadcastservice.dtos.ChargeServiceDTO;

public interface ChargeService {

    boolean consume(ChargeServiceDTO chargeServiceDTO);
}
