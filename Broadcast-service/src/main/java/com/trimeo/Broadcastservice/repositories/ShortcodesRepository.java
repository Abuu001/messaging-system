package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Shortcodes;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShortcodesRepository extends PagingAndSortingRepository<Shortcodes, Integer> {
    Optional<Shortcodes> findByName(String name);
}
