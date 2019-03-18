package com.dragonballs.services.blog;

import com.dragonballs.dao.BlogDAO;
import com.dragonballs.entities.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogDAO blogDAO;


    public List<Blog> getBlogs() {
        List<Blog> blogs = new ArrayList<>();

        for (Blog blog : blogDAO.getBlogs()) {
            blogs.add(blog);
        }
        return blogs;
    }
}
