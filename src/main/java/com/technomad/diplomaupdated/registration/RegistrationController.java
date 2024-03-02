package com.technomad.diplomaupdated.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            registrationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
    }
}
