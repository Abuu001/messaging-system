package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Networks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworksRepository extends CrudRepository<Networks, Integer> {
}
