package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.responsedatamapping.UserLocationCreator;
import com.dragonballs.services.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.jws.soap.SOAPBinding;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserLocationCreator userLocationCreator;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createResponse_ifUserProvided(){
        User user = new User();
        Mockito.when(userService.registerUser(user)).thenReturn(user);

        URI location = URI.create("location-endpoint");
        Mockito.when(userLocationCreator.userLocationCreator(user)).thenReturn(location);

        ResponseEntity responseEntity = userController.registerUser(user);

        Assert.assertEquals("[location-endpoint]", responseEntity.getHeaders().get("location").toString());
    }

    @Test
    public void createResponse_fetchUsers(){

        List<User> users = new ArrayList<>();
        Mockito.when(userService.getUsers()).thenReturn(users);

        List<User> responseEntity = userController.getUsers();

        boolean isListEmpty = responseEntity.isEmpty();

        Assert.assertEquals(true, isListEmpty);
    }
}
