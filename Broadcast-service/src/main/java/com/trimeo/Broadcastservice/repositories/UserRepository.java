package com.trimeo.Broadcastservice.repositories;

import com.trimeo.Broadcastservice.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
