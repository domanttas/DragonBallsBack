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

    public Optional<Deed> findById(Long id) {
        return deedRepository.findById(id);
    }

    public Deed save(Deed deed) {
        return deedRepository.save(deed);
    }
}
