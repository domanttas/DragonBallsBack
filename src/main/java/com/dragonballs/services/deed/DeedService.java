package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.Deed;
import com.dragonballs.entities.Participation;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeedService {
    @Autowired
    private DeedDAO deedDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DeedUtil deedUtil;

    public Deed registerDeed(DeedRequest deedRequest) {
        Deed deed = new Deed();

        deed.setName(deedRequest.getName());
        deed.setLocation(deedRequest.getLocation());
        deed.setCategory(deedRequest.getCategory());
        deed.setContact(deedRequest.getContact());
        deed.setParticipation(deedRequest.getParticipation());
        deed.setDescription(deedRequest.getDescription());
        deed.setTeamLeadId(deedRequest.getTeamLeadId());

        if (deedRequest.getTeamUsernames() == null || deedRequest.getTeamUsernames().isEmpty()) {
            throw new DeedException("No team members provided");
        }

        if (deedRequest.getParticipation() == Participation.PARTICIPATE_AS_TEAM) {
            deed.setUsers(deedUtil.fetchUsersInTeam(deedRequest.getTeamUsernames()));
            deed.setClosed(true);
        } else if (deedRequest.getParticipation() == Participation.NOT_INTERESTED) {
            deedRequest.setClosed(false);
        } else if (deedRequest.getParticipation() == Participation.PARTICIPATE_AS_SOLO) {
            deed.setUsers(deedUtil.fetchUsersInTeam(deedRequest.getTeamUsernames()));
            deedRequest.setClosed(false);
        }

        return deedDAO.registerDeed(deed);
    }

    public Long getTeamLeadId(Long deedId) {
        Optional<Deed> fetchedDeed = deedDAO.getDeedById(deedId);

        if (!fetchedDeed.isPresent()) {
            throw new DeedException("Deed does not exist");
        }

        return fetchedDeed.get().getTeamLeadId();
    }
}
