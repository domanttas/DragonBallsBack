package com.dragonballs.services.user;

import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.User;
import com.dragonballs.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    String expectedEmail = "test123@gmail.com";
    String expectedUsername = "test1234";
    String password = "12345678";

    User fakeUser;
    User savedUser;

    @Mock
    private UserDAO userDAO;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserValidator userValidator;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private UserService userService;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fakeUser = new User();
        savedUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);
    }

    @Test
    public void registerUser_should_return_registered_user() {

        Mockito.when(userDAO.registerUser(userCaptor.capture())).thenReturn(savedUser);
        Mockito.when(bCryptPasswordEncoder.encode(fakeUser.getPasswordHash())).thenReturn("encodedPassword");

        //Act
        User actualUser = userService.registerUser(fakeUser);

        //Assert
        Assert.assertEquals(expectedEmail, userCaptor.getValue().getEmail());
        Assert.assertEquals(expectedUsername, userCaptor.getValue().getUsername());
        Assert.assertEquals("encodedPassword", userCaptor.getValue().getPasswordHash());
        Assert.assertEquals(actualUser, savedUser);

        Mockito.verify(userDAO).findByEmail(expectedEmail);
    }

    @Test
    public void registerUser_should_throw_user_exists() {

        Mockito.when(userDAO.findByEmail(expectedEmail)).thenReturn(fakeUser);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_username_not_valid() {
        //Arrange
        expectedUsername = "t";

        fakeUser.setUsername(expectedUsername);

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_password_below_bottom_margin() {
        //Assert
        password = "123";

        fakeUser.setPasswordHash(password);

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_password_above_upper_margin() {
        //Arrange
        password = "12345678913545555555";

        fakeUser.setPasswordHash(password);

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_password_not_alphanumeric() {
        //Arrange
        password = "123456789@";

        fakeUser.setPasswordHash(password);

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_invalid_email() {
        //Arrange
        expectedEmail = "test123mail.com";

        fakeUser.setUsername(expectedUsername);

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void getUsers_should_return_users() {
        //Arrange
        List<User> fakeUsers = new ArrayList<>();
        fakeUsers.add(fakeUser);

        Mockito.when(userService.getUsers()).thenReturn(fakeUsers);

        List<User> actualUsers = userService.getUsers();

        Assert.assertEquals(actualUsers, fakeUsers);
    }

    @Test
    public void validateUser_should_check_if_user_exists_and_is_password_is_correct() {

        Mockito.when(userDAO.findByUsername("test1234")).thenReturn(savedUser);
        Mockito.when(bCryptPasswordEncoder.matches(fakeUser.getPasswordHash(), savedUser.getPasswordHash())).thenReturn(true);

        userService.validateUser(fakeUser);
    }

    @Test
    public void validateUser_should_should_not_find_user_in_database() {

        expectedUsername = "fakeUserName";

        Mockito.when(userDAO.findByUsername(expectedUsername)).thenReturn(null);

        Mockito.when(userService.getUserByUsername(expectedUsername)).thenThrow(UserException.class);

        thrownException.expect(UserException.class);

        userService.validateUser(savedUser);
    }

    @Test
    public void validateUser_should_throw_not_match_password() {

        Mockito.when(bCryptPasswordEncoder.matches(fakeUser.getPasswordHash(), savedUser.getPasswordHash())).thenReturn(false);

        thrownException.expect(UserException.class);

        userService.validateUser(fakeUser);
    }

}