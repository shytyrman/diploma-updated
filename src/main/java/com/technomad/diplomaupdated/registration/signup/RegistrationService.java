package com.technomad.diplomaupdated.registration.signup;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserRepository;
import com.technomad.diplomaupdated.appuser.AppUserRole;
import com.technomad.diplomaupdated.appuser.AppUserService;
import com.technomad.diplomaupdated.registration.token.ConfirmationToken;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenRepository;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public ConfirmationToken register(RegistrationRequest request) {

        AppUserRole assigningRoleValue = switch (request.role()) {
            case "admin" -> AppUserRole.ADMIN;
            case "passenger" -> AppUserRole.PASSENGER;
            case "driver" -> AppUserRole.DRIVER;
            default -> null;
        };

        if (assigningRoleValue == null) {
            throw new IllegalStateException("You haven't defined a user role!");
        }

        boolean isValidEmail = emailValidator.test(request.username());
        if (!isValidEmail) {
            throw new IllegalStateException("username not valid");
        }
        ConfirmationToken result = appUserService.signUpUser(
                new AppUser(
                        StringUtils.capitalize(request.firstName().toLowerCase()),
                        StringUtils.capitalize(request.lastName().toLowerCase()),
                        request.username().toLowerCase(),
                        request.password(),
                        assigningRoleValue
                )
        );

        return result;
    }

    @Transactional
    public AppUser confirmToken(String username, String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getTokenByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException("Activation code not found!"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Username already confirmed!");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Activation code is expired!");
        }

        if (!confirmationToken.getToken().equals(token)) {
            throw new IllegalStateException("Wrong code for username, actual code: " + confirmationTokenRepository.findByAppUserId(appUserRepository.findByUsername(username).get().getId()).orElseThrow().getToken().toString() + " , provided token: " + token);
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getUsername());
        return confirmationToken.getAppUser();
    }
}
