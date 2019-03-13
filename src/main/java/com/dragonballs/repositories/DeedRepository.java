package com.dragonballs.repositories;

import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeedRepository extends CrudRepository<Deed, Long> {

}
