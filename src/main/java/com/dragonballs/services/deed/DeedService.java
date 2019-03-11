package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.entities.Deed;
import com.dragonballs.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeedService {

    @Autowired
    private DeedDAO deedDAO;

    public List<Deed> getDeeds() {
        List<Deed> deeds = new ArrayList<>();
        for (Deed deed : deedDAO.getAllDeeds()) {
            deeds.add(deed);
        }
        return deeds;
    }

    public Deed registerDeed(Deed deed) {

        Deed existingDeed = deedDAO.findByName(deed.getName());

        if (existingDeed != null) {
            throw new UserException("User with this email already exists");
        }

        return deedDAO.registerDeed(deed);
    }
}
