package com.dragonballs.controllers;

import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import com.dragonballs.services.deed.DeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class DeedController {

    @Autowired
    private DeedService deedService;

    @PostMapping(value = "/api/deed")
    public ResponseEntity<Object> registerDeed(@RequestBody Deed deed) {
        //User savedUser = userService.registerUser(user);
        Deed savedDeed = deedService.registerDeed(deed);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDeed.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/api/deed/all")
    public List<Deed> getDeeds() {
        return this.deedService.getDeeds();
    }
}
