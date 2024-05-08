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

    @PostMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("username") String username, @RequestParam("token") String token) {
        try {
            AppUser appUser = registrationService.confirmToken(username, token);
            AppUserDto appUserDto = AppUserDto.appUserToAppUserDto(appUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(appUserDto);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
    }

    @PostMapping(path = "resend")
    public ResponseEntity<?> resend(@RequestParam("username") String username) {
        try {
            ConfirmationToken result = confirmationTokenService.resendCode(username);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }
}
