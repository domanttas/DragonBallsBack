package com.dragonballs.dao;

import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import com.dragonballs.repositories.DeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeedDAO {

    @Autowired
    private DeedRepository deedRepository;
    //getAllDeeds
    public Iterable<Deed> getAllDeeds() {
        return deedRepository.findAll();
    }

    public Deed findByName(String name) {
        return deedRepository.findByName(name);
    }

    public Deed registerDeed(Deed deed) {
        return deedRepository.save(deed);
    }


}
