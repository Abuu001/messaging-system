package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.ConsumptionRates;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRateRepository extends PagingAndSortingRepository<ConsumptionRates, Integer> {
    ConsumptionRates findByClientId(Integer clientId);
}
