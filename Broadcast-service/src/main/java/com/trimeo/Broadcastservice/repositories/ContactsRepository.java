package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Contacts;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContactsRepository extends CrudRepository<Contacts, Integer> {
    @Query(value="{ '_id' : { '$in' : ?0 }}", fields="{ 'msisdn' : 1, _id: 0}")
    Set<Contacts> findContactsBy(Set<Integer> contactId);
}
