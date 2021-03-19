package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends CrudRepository<List, Integer> {
}
