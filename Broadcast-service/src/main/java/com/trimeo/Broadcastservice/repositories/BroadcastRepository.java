package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Broadcast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BroadcastRepository extends CrudRepository<Broadcast, Integer> {
}
