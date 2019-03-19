package com.dragonballs.dao;

import com.dragonballs.entities.Deed;
import com.dragonballs.repositories.DeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<Deed> getDeeds() {
        List<Deed> deeds = new ArrayList<>();
        for (Deed deed : deedRepository.findAll()) {
            deeds.add(deed);
        }

        return deeds;
    }
}
