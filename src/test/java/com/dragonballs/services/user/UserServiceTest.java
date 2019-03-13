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
    }

    @Test
    public void registerUser_should_return_registered_user() {
        //Arrange
        String expectedEmail = "test123@gmail.com";
        String expectedUsername = "test1234";
        String password = "12345678";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        User savedUser = new User();

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
        //Arrange
        String expectedEmail = "test123@gmail.com";
        String expectedUsername = "test1234";
        String password = "12345678";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        Mockito.when(userDAO.findByEmail(expectedEmail)).thenReturn(fakeUser);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_username_not_valid() {
        //Arrange
        String expectedEmail = "test123@gmail.com";
        String expectedUsername = "t";
        String password = "12345678";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        User savedUser = new User();

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_password_below_bottom_margin() {
        //Arrange
        String expectedEmail = "test123@gmail.com";
        String expectedUsername = "test1234";
        String password = "123";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        User savedUser = new User();

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_password_above_upper_margin() {
        //Arrange
        String expectedEmail = "test123@gmail.com";
        String expectedUsername = "test1234";
        String password = "12345678913545555555";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        User savedUser = new User();

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_password_not_alphanumeric() {
        //Arrange
        String expectedEmail = "test123@gmail.com";
        String expectedUsername = "test1234";
        String password = "123456789@";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        User savedUser = new User();

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }

    @Test
    public void registerUser_should_throw_invalid_email() {
        //Arrange
        String expectedEmail = "test123mail.com";
        String expectedUsername = "test1234";
        String password = "123456789";

        User fakeUser = new User();

        fakeUser.setUsername(expectedUsername);
        fakeUser.setEmail(expectedEmail);
        fakeUser.setPasswordHash(password);

        User savedUser = new User();

        Mockito.when(userValidator.validate(fakeUser)).thenThrow(UserException.class);

        //Act and assert
        thrownException.expect(UserException.class);

        User actualUser = userService.registerUser(fakeUser);
    }
}
