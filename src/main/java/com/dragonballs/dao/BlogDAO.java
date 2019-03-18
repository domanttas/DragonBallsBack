package com.dragonballs.dao;

import com.dragonballs.entities.Blog;
import com.dragonballs.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogDAO {

    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlog(Blog blog) { return blogRepository.save(blog);}

}
