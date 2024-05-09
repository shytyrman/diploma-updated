package com.technomad.diplomaupdated.registration.signup;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserDto;
import com.technomad.diplomaupdated.registration.token.ConfirmationToken;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {
    private final String confirm = "/confirm";
    private final String resend = "/resend";

    private RegistrationService registrationService;
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        ConfirmationToken result = registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping(path = confirm)
    public ResponseEntity<?> confirm(@RequestParam("username") String username, @RequestParam("code") String token) {
        AppUser appUser = registrationService.confirmToken(username, token);
        AppUserDto appUserDto = AppUserDto.appUserToAppUserDto(appUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(appUserDto);
    }

    @PostMapping(path = resend)
    public ResponseEntity<?> resend(@RequestParam("username") String username) {
        ConfirmationToken result = confirmationTokenService.resendCode(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
