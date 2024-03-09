package com.technomad.diplomaupdated.registration;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserRole;
import com.technomad.diplomaupdated.appuser.AppUserService;
import com.technomad.diplomaupdated.registration.token.ConfirmationToken;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public void register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getUsername());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        appUserService.signUpUser(
                new AppUser(
                        "Name",
                        "Surname",
                        request.getUsername(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getUsername());
        return "confirmed";
    }
}
