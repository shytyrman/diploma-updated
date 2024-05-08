package com.technomad.diplomaupdated.appuser;

import org.springframework.security.core.userdetails.UserDetails;

public record AppUserDto(Long id, String firstName, String lastName, AppUserRole role, String username, String password) {

    public static AppUserDto appUserToAppUserDto(AppUser appUser) {

        return new AppUserDto(
                appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getAppUserRole(),
                appUser.getUsername(),
                appUser.getPassword()
        );
    }
}
