package com.dragonballs.services.blog;

import com.dragonballs.dao.BlogDAO;
import com.dragonballs.entities.Blog;
import com.dragonballs.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class BlogServiceTest {


    private Blog fakeBlog = new Blog();
    private User fakeUser = new User();

    Long expectedId = 1L;
    Byte[] expectedImageBytes = new Byte[Byte.decode("10")];
    String expectedBlogText = "Say something about fake deed.";


    @Mock
    private BlogDAO blogDAO;

    @InjectMocks
    private BlogService blogService;

    public BlogServiceTest() {
    }

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        fakeUser.setUsername("TestUsername");
        fakeUser.setEmail("TestEmail");
        fakeUser.setPasswordHash("TestPassword");

        fakeBlog.setId(expectedId);
        fakeBlog.setBlogText(expectedBlogText);
        fakeBlog.setImageBytes(expectedImageBytes);
        fakeBlog.setUser(fakeUser);
    }

    @Test
    public void getBlogs_should_return_blog_list() {
        Iterable<Blog> blogs = new ArrayList<>();
        ((ArrayList<Blog>) blogs).add(fakeBlog);
        Mockito.when(blogDAO.getBlogs()).thenReturn(blogs);

        Assert.assertEquals(blogService.getBlogs(), blogs);
    }
}
