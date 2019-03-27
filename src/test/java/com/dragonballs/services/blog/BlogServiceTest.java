package com.dragonballs.services.blog;

import com.dragonballs.dao.BlogDAO;
import com.dragonballs.entities.Blog;
import com.dragonballs.exceptions.BlogException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.sql.Date;

public class BlogServiceTest {

    private Long id;
    private String blogText;
    private Long duration;
    private Date date;
    private Byte[] imageBytes;

    private Blog fakeBlog = new Blog();

    @Captor
    private ArgumentCaptor<Blog> blogArgumentCaptor;

    @Mock
    private BlogDAO blogDAO;

    @Mock
    private BlogValidator blogValidator;

    @InjectMocks
    private BlogService blogService;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        id = 1L;
        blogText = "testBlogText";
        duration = 1L;
        date = new Date(2011,8,9);

        fakeBlog.setId(id);
        fakeBlog.setBlogText(blogText);
        fakeBlog.setDuration(duration);
        fakeBlog.setDate(date);
    }

    @Test
    public void createBlog_should_return_blog() {
        Blog captureBlog = blogArgumentCaptor.capture();
        Mockito.when(blogDAO.createBlog(captureBlog)).thenReturn(fakeBlog);
        Mockito.when(blogValidator.validate(captureBlog)).thenReturn(true);

        Blog result = blogService.createBlog(captureBlog);

        Assert.assertEquals(fakeBlog, result);
    }

    @Test
    public void createBlog_should_throw() {
        Blog captureBlog = blogArgumentCaptor.capture();
        Mockito.when(blogDAO.createBlog(captureBlog)).thenReturn(fakeBlog);
        Mockito.when(blogValidator.validate(captureBlog)).thenThrow(BlogException.class);

        thrownException.expect(BlogException.class);
        blogService.createBlog(captureBlog);
    }
}
