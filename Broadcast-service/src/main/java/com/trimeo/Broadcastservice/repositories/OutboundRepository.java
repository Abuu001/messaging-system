package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Outbound;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundRepository extends CrudRepository<Outbound, Integer> {
}
