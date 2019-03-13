package com.dragonballs.dao;

import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import com.dragonballs.exceptions.DeedException;
import com.dragonballs.repositories.DeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Deed findById(Long id) {
        Optional<Deed> maybeDeed = deedRepository.findById(id);
        if(maybeDeed.isPresent()){
            return maybeDeed.get();
        }

        throw new DeedException("message");
//        return deedRepository.findById(id);
    }

    public Deed registerDeed(Deed deed) {
        return deedRepository.save(deed);
    }

    public Deed update(Deed deed){
        return deedRepository.save(deed);
    }

}
