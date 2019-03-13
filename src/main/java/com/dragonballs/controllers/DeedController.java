package com.dragonballs.controllers;

import com.dragonballs.entities.Deed;
import com.dragonballs.services.deed.DeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeedController {

    @Autowired
    private DeedService deedService;

    @PutMapping(value = "api/deed/update")
    public ResponseEntity<Object> updateDeed (@RequestBody Deed deed){

        Deed fetchedDeed = deedService.updateDeed(deed);
        return ResponseEntity.ok().body(fetchedDeed);
    }
}
