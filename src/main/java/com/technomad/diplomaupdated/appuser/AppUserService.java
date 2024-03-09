package com.technomad.diplomaupdated.appuser;

import com.technomad.diplomaupdated.additional.CodeGenerator;
import com.technomad.diplomaupdated.registration.token.ConfirmationToken;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenRepository;
import com.technomad.diplomaupdated.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE =
            "user with email %s not found!";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    @Transactional
    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();

        if (userExists && appUser.getEnabled()) {
            throw new IllegalStateException("email already taken");
        }
        else if (userExists && appUser.getEnabled() == false) {
            AppUser existingOne = appUserRepository.findByUsername(appUser.getUsername()).get();
            System.out.println("deleting");
            confirmationTokenRepository.deleteByAppUserId(existingOne.getId());
            appUserRepository.delete(existingOne);
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = CodeGenerator.generate();

        // TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        TODO: SEND EMAIL
        return token;
    }

    public int enableAppUser(String user) {
        return appUserRepository.enableAppUser(user);
    }

    public void changeName(AppUser appUser, String name) {
        appUser.setFirstName(name);
        appUserRepository.save(appUser);
    }

    public void changeSurname(AppUser appUser, String surname) {
        appUser.setLastName(surname);
        appUserRepository.save(appUser);
    }
}
