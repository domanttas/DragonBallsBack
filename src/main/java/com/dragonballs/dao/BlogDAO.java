package com.dragonballs.dao;

import com.dragonballs.entities.Blog;
import com.dragonballs.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogDAO {

    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getBlogs() {

        List<Blog> blogs = new ArrayList<>();

        for (Blog blog : blogRepository.findAll()) {
            blogs.add(blog);
        }

        return blogs;
    }

    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }
}
