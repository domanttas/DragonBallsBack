package com.dragonballs.controllers;

import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.TeamMembersException;
import com.dragonballs.services.deed.DeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/deed")
public class DeedController {

    @Autowired
    private DeedService deedService;

    @PostMapping
    public ResponseEntity<?> registerDeed(@RequestBody DeedRequest deedRequest) {
        try {
            deedService.registerDeed(deedRequest);
        } catch (TeamMembersException missingUserException) {
            return ResponseEntity.badRequest().body(missingUserException.getMissingUsers());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add/{id}")
    public ResponseEntity<?> addUserToDeed(@RequestBody User user, @PathVariable Long id) {
        Deed updatedDeed = deedService.addUserToDeed(user, id);

        return ResponseEntity.ok().body(updatedDeed);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateDeed(@RequestBody Deed deed) {
        Deed fetchedDeed = deedService.updateDeed(deed);

        return ResponseEntity.ok().body(fetchedDeed);
    }

    @GetMapping
    public ResponseEntity<?> getDeeds() {
        return ResponseEntity.ok().body(deedService.getDeeds());
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<?> deactivateDeed(@PathVariable("id") Long id) {
        deedService.alterDeedStatus(id);

        return ResponseEntity.ok().build();
    }
}
