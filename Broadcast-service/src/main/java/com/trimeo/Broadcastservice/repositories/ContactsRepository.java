package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Contacts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends CrudRepository<Contacts, Integer> {
}
