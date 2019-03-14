package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.Deed;
import com.dragonballs.entities.Participation;
import com.dragonballs.entities.User;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
import com.dragonballs.exceptions.TeamMembersException;
import com.dragonballs.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeedService {
    @Autowired
    private DeedDAO deedDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DeedUtil deedUtil;

    public Deed registerDeed(DeedRequest deedRequest) throws TeamMembersException {
        Deed deed = new Deed();

        deed.setName(deedRequest.getName());
        deed.setLocation(deedRequest.getLocation());
        deed.setCategory(deedRequest.getCategory());
        deed.setContact(deedRequest.getContact());
        deed.setParticipation(deedRequest.getParticipation());
        deed.setDescription(deedRequest.getDescription());
        deed.setTeamLeadId(deedRequest.getTeamLeadId());

        if (deedRequest.getParticipation() == Participation.PARTICIPATE_AS_TEAM) {
            if (deedRequest.getTeamUsernames() == null || deedRequest.getTeamUsernames().isEmpty()) {
                throw new DeedException("No team members provided");
            }

            deed.setUsers(deedUtil.fetchUsersInTeam(deedRequest.getTeamUsernames()));
            deed.setClosed(true);
        } else if (deedRequest.getParticipation() == Participation.NOT_INTERESTED) {
            deedRequest.setClosed(false);
        } else if (deedRequest.getParticipation() == Participation.PARTICIPATE_AS_SOLO) {
            if (deedRequest.getTeamUsernames() == null || deedRequest.getTeamUsernames().isEmpty()) {
                throw new DeedException("No team members provided");
            }

            try {
                deed.setUsers(deedUtil.fetchUsersInTeam(deedRequest.getTeamUsernames()));
            } catch (TeamMembersException missingUsersException) {
                throw new TeamMembersException(missingUsersException.getMissingUsers());
            }

            deedRequest.setClosed(false);
        }

        return deedDAO.registerDeed(deed);
    }

    public Deed addUserToDeed(User user, Long deedId) {
        Optional<User> fetchedUser = userDAO.findById(user.getId());

        if (!fetchedUser.isPresent()) {
            throw new UserException("User does not exist");
        }

        Optional<Deed> fetchedDeed = deedDAO.getDeedById(deedId);

        if (!fetchedDeed.isPresent()) {
            throw new DeedException("Deed does not exist");
        }

        fetchedDeed.get().getUsers().add(fetchedUser.get());

        return deedDAO.registerDeed(fetchedDeed.get());
    }

    public Long getTeamLeadId(Long deedId) {
        Optional<Deed> fetchedDeed = deedDAO.getDeedById(deedId);

        if (!fetchedDeed.isPresent()) {
            throw new DeedException("Deed does not exist");
        }

        return fetchedDeed.get().getTeamLeadId();
    }

    public Deed updateDeed(Deed deed) {
        Optional<Deed> maybeDeed = deedDAO.getDeedById(deed.getId());

        if (!maybeDeed.isPresent()) {
            throw new DeedException("Such deed does not exist.");
        }

        return deedDAO.registerDeed(deed);
    }

    public void alterDeedStatus(Long id) {
        Optional<Deed> deed = deedDAO.getDeedById(id);

        if (!deed.isPresent()) {
            throw new DeedException("Deed with this id does not exist");
        }

        deed.get().setClosed(true);

        deedDAO.registerDeed(deed.get());
    }

    public List<Deed> getDeeds() {
        List<Deed> deeds = new ArrayList<>();

        for (Deed deed : deedDAO.getDeeds()) {
            deeds.add(deed);
        }

        return deeds;
    }
}
