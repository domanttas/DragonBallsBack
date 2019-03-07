package com.dragonballs.responsedatamapping;

import com.dragonballs.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class UserLocationCreator {

    public URI userLocationCreator(User savedUser){
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return location;
    }
}
