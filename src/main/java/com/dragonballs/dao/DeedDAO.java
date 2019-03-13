package com.dragonballs.dao;

import com.dragonballs.entities.Deed;
import com.dragonballs.repositories.DeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeedDAO {

    @Autowired
    private DeedRepository deedRepository;

    public Optional<Deed> getDeedById(Long id) {
        return deedRepository.findById(id);
    }

    public Deed registerDeed(Deed deed) {
        return deedRepository.save(deed);
    }
}
