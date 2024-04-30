package com.notetakingappca.api.key;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/key")
@AllArgsConstructor
public class KeyController {

    private final KeyService keyService;

    @GetMapping("/{userId}")
    public ResponseEntity<Key> getKey(@PathVariable String userId) {
        return new ResponseEntity<>(keyService.getKey(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Key> createKey(@PathVariable String userId) throws Exception {
        return new ResponseEntity<>(keyService.createKey(userId), HttpStatus.OK);
    }

}
