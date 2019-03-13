package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.*;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    private Optional<Deed> fakeDeed = Optional.ofNullable(new Deed());
    private DeedRequest deedRequest = new DeedRequest();
    private User user;

    @Mock
    private UserDAO userDAO;

    @Mock
    private DeedDAO deedDAO;

    @Mock
    private DeedUtil deedUtil;

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

        fakeDeed.get().setId(id);
        fakeDeed.get().setName(name);
        fakeDeed.get().setDescription(description);
        fakeDeed.get().setDescription(description);
        fakeDeed.get().setLocation(location);
        fakeDeed.get().setClosed(isClosed);
        fakeDeed.get().setCategory(category);
        fakeDeed.get().setContact(contact);
        fakeDeed.get().setUsers(users);
        fakeDeed.get().setTeamLeadId(3L);
        fakeDeed.get().setParticipation(Participation.PARTICIPATE_AS_TEAM);

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
    public void getTeamLeadId_should_return_id() {
        Long fakeDeedId = 1L;
        Long teamLeadId = 3L;

        Mockito.when(deedDAO.getDeedById(fakeDeedId)).thenReturn(fakeDeed);

        Long returnedId = deedService.getTeamLeadId(fakeDeedId);

        Assert.assertEquals(teamLeadId, returnedId);
    }

    @Test
    public void getTeamLeadId_should_throw() {
        Long fakeDeedId = 1L;

        Mockito.when(deedDAO.getDeedById(fakeDeedId)).thenThrow(DeedException.class);

        thrownException.expect(DeedException.class);

        deedService.getTeamLeadId(fakeDeedId);
    }
}
