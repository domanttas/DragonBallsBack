package com.dragonballs.repositories;

import com.dragonballs.entities.Deed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeedRepository extends CrudRepository<Deed, Long> {

    Deed findByName(String name);
}
