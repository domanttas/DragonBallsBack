package com.dragonballs.controllers;

import com.dragonballs.entities.Deed;
import com.dragonballs.entities.User;
import com.dragonballs.entities.request.DeedRequest;
import com.dragonballs.exceptions.TeamMembersException;
import com.dragonballs.services.deed.DeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTeamLeadId(@PathVariable Long id) {
        Long teamLeadId = deedService.getTeamLeadId(id);

        return ResponseEntity.ok().body(teamLeadId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> addUserToDeed(@RequestBody User user, @PathVariable Long id) {
        Deed updatedDeed = deedService.addUserToDeed(user, id);

        return ResponseEntity.ok().body(updatedDeed);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateDeed (@RequestBody Deed deed){
        Deed fetchedDeed = deedService.updateDeed(deed);
        return ResponseEntity.ok().body(fetchedDeed);
    }

    @GetMapping
    public ResponseEntity<?> getDeeds() {
        return ResponseEntity.ok().body(deedService.getDeeds());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deactivateDeed(@PathVariable("id") Long id){
        deedService.alterDeedStatus(id);
        return ResponseEntity.ok().body(null);
    }
}
