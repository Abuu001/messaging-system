package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.Contactlist;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface ContactlistRepository extends CrudRepository<Contactlist, Integer> {

    @Query(value="{ 'listID' : { '$in' : ?0 }}", fields="{ 'contactID' : 1, _id: 0}")
    Set<Contactlist> findContactId(ArrayList<Integer> listIds);
}
