package com.technomad.diplomaupdated.settings;

import com.technomad.diplomaupdated.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/settings")
@AllArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @PostMapping(path = "/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal AppUser appUser, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        try {
            String result = settingsService.changeNameAndSurname(appUser, firstName, lastName);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
