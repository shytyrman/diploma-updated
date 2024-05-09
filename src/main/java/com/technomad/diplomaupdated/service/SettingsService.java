package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SettingsService {

    public static final String CHANGED_RESPONSE = "changed!";
    private final AppUserService appUserService;

    public String changeNameAndSurname(AppUser appUser, String name, String surname) {
        appUserService.changeName(appUser, name);
        appUserService.changeSurname(appUser, surname);
        return CHANGED_RESPONSE;
    }
}
