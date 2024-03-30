package com.technomad.diplomaupdated.registration;

import com.technomad.diplomaupdated.registration.token.ConfirmationToken;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenService;
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
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            ConfirmationToken result = registrationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("username") String username, @RequestParam("token") String token) {
        try {
            String result = registrationService.confirmToken(username, token);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @PostMapping(path = "resend")
    public ResponseEntity<?> resend(@RequestParam("username") String username) {
        try {
            String result = confirmationTokenService.resendCode(username);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
