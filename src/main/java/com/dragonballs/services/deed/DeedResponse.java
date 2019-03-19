package com.dragonballs.services.deed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Service
public class DeedResponse {

    @PostMapping(value="api/deed/")
    public ResponseEntity<List<String>> createDeedResponse(List<String> missingUsers) {

        return new ResponseEntity<> (missingUsers, HttpStatus.BAD_REQUEST);
    }
}
