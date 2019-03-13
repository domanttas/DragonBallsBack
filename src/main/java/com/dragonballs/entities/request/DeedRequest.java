package com.dragonballs.entities.request;

import com.dragonballs.entities.Category;
import com.dragonballs.entities.Contact;
import com.dragonballs.entities.Participation;

import java.util.List;

public class DeedRequest {

    private String name;

    private String description;

    private String location;

    private boolean isClosed;

    private Long teamLeadId;

    private Category category;

    private Contact contact;

    private Participation participation;

    private List<String> teamUsernames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Long getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(Long teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public List<String> getTeamUsernames() {
        return teamUsernames;
    }

    public void setTeamUsernames(List<String> teamUsernames) {
        this.teamUsernames = teamUsernames;
    }
}
