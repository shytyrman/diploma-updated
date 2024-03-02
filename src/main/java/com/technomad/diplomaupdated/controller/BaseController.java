package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class BaseController {

    @GetMapping
    public ResponseEntity<?> base() {
        return ResponseEntity.status(HttpStatus.CREATED).body("Logged succesfully!");
    }
}
