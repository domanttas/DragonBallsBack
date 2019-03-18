package com.dragonballs.controllers;

import com.dragonballs.entities.Blog;
import com.dragonballs.services.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        Blog fetchedBlog = blogService.createBlog(blog);
        return ResponseEntity.ok().body(fetchedBlog);
    }
}
