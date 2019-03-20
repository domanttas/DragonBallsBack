package com.dragonballs.services.blog;

import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.Blog;
import com.dragonballs.entities.User;
import com.dragonballs.exceptions.BlogException;
import com.dragonballs.exceptions.UserException;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BlogValidator {

    @Autowired
    private UserDAO userDAO;

    private static final String ALL_FIELD_ARE_REQUIRED = "All field are required";

    public boolean validate(Blog blog) {

        if (blog.getBlogText().isEmpty()) {
            throw new BlogException(ALL_FIELD_ARE_REQUIRED);
        } else if (blog.getDuration() == 0) {
            throw new BlogException(ALL_FIELD_ARE_REQUIRED);
        }

        validateUser(blog.getUser());

        return true;
    }

    private void validateUser(User user) {
        User existingUser = userDAO.findByUsername(user.getUsername());

        if (existingUser == null) {
            throw new UserException("User does not exist");
        }
    }
}
