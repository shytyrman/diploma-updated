package com.technomad.diplomaupdated.registration.signup;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserDto;
import com.technomad.diplomaupdated.registration.token.ConfirmationToken;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {
    private final String registration = "/registration";
    private final String confirm = "/confirm";
    private final String resend = "/resend";

    private RegistrationService registrationService;
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            ConfirmationToken result = registrationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        catch (Exception e){
            RegistrationExceptionResponse response = new RegistrationExceptionResponse(
                    LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.name(),
                    e.getMessage(),
                    registration
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping(path = confirm)
    public ResponseEntity<?> confirm(@RequestParam("username") String username, @RequestParam("token") String token) {
        try {
            AppUser appUser = registrationService.confirmToken(username, token);
            AppUserDto appUserDto = AppUserDto.appUserToAppUserDto(appUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(appUserDto);
        }
        catch (Exception e) {
            RegistrationExceptionResponse response = new RegistrationExceptionResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_ACCEPTABLE.value(),
                    HttpStatus.NOT_ACCEPTABLE.name(),
                    e.getMessage(),
                    registration + confirm
            );
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }
    }

    @PostMapping(path = resend)
    public ResponseEntity<?> resend(@RequestParam("username") String username) {
        try {
            ConfirmationToken result = confirmationTokenService.resendCode(username);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch (Exception e) {
            RegistrationExceptionResponse response = new RegistrationExceptionResponse(
                    LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.name(),
                    e.getMessage(),
                    registration + resend
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
