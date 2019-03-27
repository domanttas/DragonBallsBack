package com.dragonballs.exceptions;

import java.util.ArrayList;
import java.util.List;

public class TeamMembersException extends Exception {

    private List<String> missingUsers = new ArrayList<>();

    public TeamMembersException(List<String> missingUsers) {
        this.missingUsers = missingUsers;
    }

    public TeamMembersException() {
    }

    public List<String> getMissingUsers() {
        return missingUsers;
    }

    public void setMissingUsers(List<String> missingUsers) {
        this.missingUsers = missingUsers;
    }
}
