package com.dragonballs.services.deed;

import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.User;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
import com.dragonballs.exceptions.TeamMembersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeedUtil {

    @Autowired
    private UserDAO userDAO;

    public List<User> fetchUsersInTeam(List<String> usernames) throws TeamMembersException {
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

            throw new TeamMembersException(missingUsers);
        }

        return fetchedUsers;
    }
}
