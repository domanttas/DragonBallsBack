package com.dragonballs.controllers;

import com.dragonballs.controllers.DeedController;
import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.DeedException;
import com.dragonballs.exceptions.TeamMembersException;
import com.dragonballs.services.deed.DeedService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeedControllerTest {

    @Mock
    private DeedService deedService;

    @InjectMocks
    private DeedController deedController;

    @Captor
    private ArgumentCaptor<DeedRequest> deedRequest;

    @Captor
    private ArgumentCaptor<Deed> deed;

    @Captor
    private ArgumentCaptor<User> user;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerDeed_should_return_ok() throws TeamMembersException {
        DeedRequest fakeRequest = deedRequest.capture();
        Mockito.when(deedService.registerDeed(fakeRequest)).thenReturn(deed.capture());

        ResponseEntity result = deedController.registerDeed(fakeRequest);

        Assert.assertEquals(ResponseEntity.ok().body(null), result);
    }

    @Test
    public void registerDeed_should_throw() throws TeamMembersException {
        DeedRequest fakeRequest = null;

        Mockito.when(deedService.registerDeed(fakeRequest)).thenThrow(DeedException.class);
        thrownException.expect(DeedException.class);

        deedController.registerDeed(fakeRequest);
    }

    @Test
    public void addUserToDeed_should_return_ok() {
        Long deedId = 5L;
        User fakeUser = user.capture();
        Deed fakeDeed = deed.capture();
        Mockito.when(deedService.addUserToDeed(fakeUser, deedId)).thenReturn(fakeDeed);

        ResponseEntity result = deedController.addUserToDeed(fakeUser, deedId);

        Assert.assertEquals(ResponseEntity.ok().body(fakeDeed), result);
    }




    @Test
    public void updateDeed_should_return_ok() {
        Deed fakeDeed = deed.capture();
        Mockito.when(deedService.updateDeed(fakeDeed)).thenReturn(fakeDeed);

        ResponseEntity result = deedController.updateDeed(fakeDeed);

        Assert.assertEquals(ResponseEntity.ok().body(fakeDeed), result);
    }

    @Test
    public void getDeeds_should_return_ok() {
        ResponseEntity result = deedController.getDeeds();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
