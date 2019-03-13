package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.entities.Deed;
import com.dragonballs.exceptions.DeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeedService {

    @Autowired
    private DeedDAO deedDAO;

    public Deed findById(Long id) {
        Optional<Deed> maybeDeed = deedDAO.findById(id);

        if (maybeDeed.isPresent()) {
            return maybeDeed.get();
        }
        throw new DeedException("Such deed does not exist.");
    }

    public Deed updateDeed(Deed deed){
        findById(deed.getId());
        return deedDAO.save(deed);
    }
}
