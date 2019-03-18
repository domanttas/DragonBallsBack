package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.*;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
import com.dragonballs.exceptions.TeamMembersException;
import com.dragonballs.exceptions.UserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Verifier;
import org.mockito.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeedServiceTest {

    private Long id;
    private String name;
    private String description;
    private String location;
    private boolean isClosed;
    private Category category;
    private Contact contact;
    private Participation participation;
    private List<User> users;
    private List<String> usernames;

    private Deed fakedDeed = new Deed();
    private DeedRequest deedRequest = new DeedRequest();
    private User user;

    @Mock
    DeedUtil deedUtil;

    @Mock
    private UserDAO userDAO;

    @Mock
    private DeedDAO deedDAO;

    @InjectMocks
    private DeedService deedService;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        id = 1L;
        name = "testName";
        description = "testDescription";
        location = "testLocation";
        isClosed = false;
        category = new Category();
        contact = new Contact();

        users = new ArrayList<>();

        usernames = new ArrayList<>();
        usernames.add("TestUsername");

        fakedDeed.setId(id);
        fakedDeed.setName(name);
        fakedDeed.setDescription(description);
        fakedDeed.setDescription(description);
        fakedDeed.setLocation(location);
        fakedDeed.setClosed(isClosed);
        fakedDeed.setCategory(category);
        fakedDeed.setContact(contact);
        fakedDeed.setUsers(users);
        fakedDeed.setTeamLeadId(3L);
        fakedDeed.setParticipation(Participation.PARTICIPATE_AS_TEAM);

        deedRequest.setName(name);
        deedRequest.setDescription(description);
        deedRequest.setDescription(description);
        deedRequest.setLocation(location);
        deedRequest.setClosed(isClosed);
        deedRequest.setCategory(category);
        deedRequest.setContact(contact);
        deedRequest.setTeamUsernames(usernames);
        deedRequest.setTeamLeadId(3L);
        deedRequest.setParticipation(Participation.PARTICIPATE_AS_TEAM);

        user = new User();
        user.setUsername("TestUsername");
        user.setEmail("TestEmail");
        user.setPasswordHash("TestPassword");
        users.add(user);
    }


    @Test
    public void registerDeed_should_return_registered_deed() throws TeamMembersException {
        Mockito.when(deedDAO.registerDeed(fakedDeed)).thenReturn(fakedDeed);

        deedService.registerDeed(deedRequest);

        Assert.assertEquals(deedRequest.getName(), fakedDeed.getName());
    }

    @Test
    public void registerDeed_should_throw_exception_No_team_members_provided() throws TeamMembersException {
        deedRequest.setTeamUsernames(null);
        fakedDeed.setUsers(null);

        Mockito.when(deedDAO.registerDeed(fakedDeed)).thenReturn(fakedDeed);

        thrownException.expect(DeedException.class);
        thrownException.expectMessage("No team members provided");

        deedService.registerDeed(deedRequest);

    }

    @Test
    public void registerDeed_should_close_deed_when_participation_not_interested() throws TeamMembersException {
        deedRequest.setParticipation(Participation.NOT_INTERESTED);
        Mockito.when(deedDAO.registerDeed(fakedDeed)).thenReturn(fakedDeed);

        deedService.registerDeed(deedRequest);

        Assert.assertEquals(deedRequest.getParticipation(), Participation.NOT_INTERESTED);

    }

    @Test
    public void registerDeed_should_close_deed_when_participation_as_solo() throws TeamMembersException {
        deedRequest.setParticipation(Participation.PARTICIPATE_AS_SOLO);
        Mockito.when(deedDAO.registerDeed(fakedDeed)).thenReturn(fakedDeed);

        deedService.registerDeed(deedRequest);

        Assert.assertEquals(deedRequest.getParticipation(), Participation.PARTICIPATE_AS_SOLO);
    }

    @Test
    public void addUserToDeed_should_return_added_user() {
        Mockito.when(userDAO.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(deedDAO.getDeedById(fakedDeed.getId())).thenReturn(Optional.of(fakedDeed));

        fakedDeed.getUsers().add(user);
        deedDAO.registerDeed(fakedDeed);

        deedService.addUserToDeed(user, fakedDeed.getId());
    }

    @Test
    public void addUserToDeed_should_throw_user_does_not_exist_exception() {
        Mockito.when(userDAO.findById(user.getId())).thenReturn(Optional.empty());
        thrownException.expect(UserException.class);

        deedService.addUserToDeed(user, fakedDeed.getId());

    }

    @Test
    public void addUserToDeed_should_throw_deed_does_not_exist_exception() {
        Mockito.when(userDAO.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(deedDAO.getDeedById(fakedDeed.getId())).thenReturn(Optional.empty());

        thrownException.expect(DeedException.class);

        deedService.addUserToDeed(user, fakedDeed.getId());
    }

    @Test
    public void updateDeed_should_throw_user_does_not_exist_exception() {
        Mockito.when(deedDAO.getDeedById(fakedDeed.getId())).thenReturn(Optional.empty());

        thrownException.expect(DeedException.class);

        deedService.updateDeed(fakedDeed);
    }

    @Test
    public void updateDeed_should_return_update_deed() {
        String newFakeName = "newFakeName";
        fakedDeed.setName(newFakeName);

        Mockito.when(deedDAO.getDeedById(fakedDeed.getId())).thenReturn(Optional.of(fakedDeed));
        Mockito.when(deedDAO.registerDeed(fakedDeed)).thenReturn(fakedDeed);

        deedService.updateDeed(fakedDeed);

        Assert.assertEquals(fakedDeed.getName(), newFakeName);
    }

    @Test
    public void getDeeds_should_return_deed_list() {
        Iterable<Deed> deeds = new ArrayList<>();
        ((ArrayList<Deed>) deeds).add(fakedDeed);

        Mockito.when(deedDAO.getDeeds()).thenReturn(deeds);

        Assert.assertEquals(deedService.getDeeds(), deeds);
    }

    @Test
    public void alterDeedStatus_should_change_deed_status() {
        Mockito.when(deedDAO.getDeedById(fakedDeed.getId())).thenReturn(Optional.of(fakedDeed));
        fakedDeed.setClosed(true);
        Mockito.when(deedDAO.registerDeed(fakedDeed)).thenReturn(fakedDeed);

        deedService.alterDeedStatus(fakedDeed.getId());

        Assert.assertEquals(fakedDeed.isClosed(), true);
    }

    @Test
    public void alterDeedStatus_should_throw_exception() {
        Mockito.when(deedDAO.getDeedById(fakedDeed.getId())).thenReturn(Optional.empty());
        thrownException.expect(DeedException.class);

        deedService.alterDeedStatus(fakedDeed.getId());
    }
}
