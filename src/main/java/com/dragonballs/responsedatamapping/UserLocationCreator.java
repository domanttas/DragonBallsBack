package com.dragonballs.responsedatamapping;

import com.dragonballs.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class UserLocationCreator {

    public URI userLocationCreator(User user){
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return location;
    }
}
