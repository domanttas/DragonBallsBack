package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.*;
import com.dragonballs.entities.request.DeedRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

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

    @Mock
    private UserDAO userDAO;

    @Mock
    private DeedDAO deedDAO;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<Deed> deedCaptor;

    @Captor ArgumentCaptor<DeedRequest> deedRequestCaptor;

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
    }

    @Test
    public void registerDeed_as_team_should_return_deed() {
//        participation = Participation.PARTICIPATE_AS_TEAM;
//        Deed savedDeed = new Deed();
//        Mockito.when(deedDAO.registerDeed(deedCaptor.capture())).thenReturn(savedDeed);
//        Deed actualDeed = deedService.registerDeed(deedRequestCaptor.capture());
//        Assert.assertEquals(actualDeed, savedDeed);
    }
}
