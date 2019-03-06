package com.dragonballs.repositories;

import com.dragonballs.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findBySessionToken(String sessionToken);

    User findByUsername(String username);
}
