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

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Iterable<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }
}
