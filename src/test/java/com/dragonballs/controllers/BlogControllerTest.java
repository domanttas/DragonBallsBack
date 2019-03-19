package com.dragonballs.controllers;

import com.dragonballs.controllers.BlogController;
import com.dragonballs.entities.Blog;
import com.dragonballs.entities.User;
import com.dragonballs.services.blog.BlogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BlogControllerTest {

    @Mock
    BlogService blogService;

    @Captor
    private ArgumentCaptor<Blog> blog;

    @InjectMocks
    private BlogController blogController;

    @Captor
    private ArgumentCaptor<User> user;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getBlogs_should_return_ok() {

        ResponseEntity result = blogController.getBlogs();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
