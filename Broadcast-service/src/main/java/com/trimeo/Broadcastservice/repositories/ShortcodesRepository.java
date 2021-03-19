package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Shortcodes;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortcodesRepository extends PagingAndSortingRepository<Shortcodes, Integer> {
    Optional<Shortcodes> findByName(String name);
}
