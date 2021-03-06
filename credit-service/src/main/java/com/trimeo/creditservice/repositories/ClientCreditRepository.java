package com.trimeo.creditservice.repositories;

import com.trimeo.creditservice.domains.Credits;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientCreditRepository extends CrudRepository<Credits, Integer> {
    Optional<Credits> findByClientCode(String clientCode);
}
