package com.dragonballs.controllers.blog;
import com.dragonballs.controllers.BlogController;
import com.dragonballs.entities.Blog;
import com.dragonballs.exceptions.BlogException;
import com.dragonballs.services.blog.BlogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class BlogControllerTest {

    @Mock
    private BlogService blogService;

    @InjectMocks
    private BlogController blogController;

    @Captor
    private ArgumentCaptor<Blog> blog;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    private Blog fakeBlog;

    private List<Blog> fakeBlogList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fakeBlog = blog.capture();
        fakeBlogList = new ArrayList<>();
        fakeBlogList.add(fakeBlog);
    }

    @Test
    public void createBlog_should_return_blog() {
        Mockito.when(blogService.createBlog(fakeBlog)).thenReturn(fakeBlog);
        ResponseEntity result = blogController.createBlog(fakeBlog);

        Assert.assertEquals(fakeBlog, result.getBody());
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createBlog_should_throw() {
        Mockito.when(blogService.createBlog((fakeBlog))).thenThrow(BlogException.class);
        thrownException.expect(BlogException.class);
        blogController.createBlog(fakeBlog);
    }
}
