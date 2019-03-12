package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.Deed;
import com.dragonballs.entities.Participation;
import com.dragonballs.entities.User;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
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

    public Deed registerDeed(DeedRequest deedRequest) {
        Deed deed = new Deed();

        if (deedRequest.getParticipation() == Participation.PARTICIPATE_AS_TEAM) {
            if (deedRequest.getTeamUsernames() == null || deedRequest.getTeamUsernames().isEmpty()) {
                throw new DeedException("No team members provided");
            }

            deed.setName(deedRequest.getName());
            deed.setLocation(deedRequest.getLocation());
            deed.setCategory(deedRequest.getCategory());
            deed.setContact(deedRequest.getContact());
            deed.setParticipation(deedRequest.getParticipation());
            deed.setDescription(deedRequest.getDescription());
            deed.setTeamLeadId(deedRequest.getTeamLeadId());

            deed.setUsers(fetchUsersInTeam(deedRequest.getTeamUsernames()));

            deed.setClosed(true);
        } else {
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

    private List<User> fetchUsersInTeam(List<String> usernames) {
        List<User> fetchedUsers = new ArrayList<>();
        List<String> missingUsers = new ArrayList<>();

        User fetchedUser;

        for (String username : usernames) {
            fetchedUser = userDAO.findByUsername(username);

            if (fetchedUser == null) {
                missingUsers.add(username);
            } else {
                fetchedUsers.add(fetchedUser);
            }
        }

        if (!missingUsers.isEmpty()) {
            throw new DeedException("Missing users: ", missingUsers);
        }

        return fetchedUsers;
    }
}
