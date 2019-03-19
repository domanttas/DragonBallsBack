package com.dragonballs.controllers;

import com.dragonballs.entities.Blog;
import com.dragonballs.services.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<?> getBlogs() {
        return ResponseEntity.ok().body(blogService.getBlogs());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> deleteBlogById(@PathVariable Long id) {
        blogService.deleteBlogById(id);
        return ResponseEntity.ok().build();
    }
}
