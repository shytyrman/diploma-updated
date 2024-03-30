package com.technomad.diplomaupdated.registration.token;

import com.technomad.diplomaupdated.additional.CodeGenerator;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getTokenByUsername(String username) {
        return confirmationTokenRepository.findByAppUserId(appUserRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("No such account with token!")).getId());
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public String resendCode(String username) {

        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        Long appUserId = appUser.get().getId();

        Optional<ConfirmationToken> token = confirmationTokenRepository.findByAppUserId(appUserId);
        boolean tokenExists = token.isPresent();

        if (tokenExists && !appUser.get().getEnabled()) {
            ConfirmationToken toDelete = token.get();
            confirmationTokenRepository.delete(toDelete);
        } else if (tokenExists) {
            throw new IllegalStateException("The profile is confirmed already!");
        }

        String code = CodeGenerator.generate();

        // TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                code,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser.get()
        );

        saveConfirmationToken(confirmationToken);

        return code;
    }
}
