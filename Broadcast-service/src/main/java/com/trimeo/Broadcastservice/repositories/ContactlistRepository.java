package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Contactlist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactlistRepository extends PagingAndSortingRepository<Contactlist, Integer> {
}
