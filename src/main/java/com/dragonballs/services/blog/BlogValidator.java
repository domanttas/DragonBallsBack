package com.dragonballs.services.blog;

import com.dragonballs.entities.Blog;
import com.dragonballs.exceptions.BlogException;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogValidator {

    @Autowired
    private UserService userService;

    private static final String ALL_FIELD_ARE_REQUIRED = "All field are required";

    public boolean validate (Blog blog) {

        if (blog.getBlogText().isEmpty()) {
            throw new BlogException(ALL_FIELD_ARE_REQUIRED);
        } else if (blog.getDuration() == 0) {
            throw new BlogException(ALL_FIELD_ARE_REQUIRED);
        }
        userService.validateUser(blog.getUser());
        return true;
    }
}
