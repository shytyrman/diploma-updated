package com.technomad.diplomaupdated.registration;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserRole;
import com.technomad.diplomaupdated.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getUsername());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                    "Name",
                    "Surname",
                    request.getUsername(),
                    request.getPassword(),
                    AppUserRole.USER
                )
        );
    }
}
