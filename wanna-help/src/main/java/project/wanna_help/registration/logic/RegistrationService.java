package project.wanna_help.registration.logic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.registration.persistence.domain.AppUser;
import project.wanna_help.registration.persistence.repository.AppUserRepository;

@Service
@Validated
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrationService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(AppUser appUser) {

        if (isPasswordValid(appUser.getPassword())) {
            String encodedPassword = passwordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);
            return appUserRepository.save(appUser);
        }
        throw new IllegalArgumentException("Password must have at least one character and one digit");

    }

    private boolean isPasswordValid(String password) {
        boolean hasDigit = false;
        boolean hasLetter = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }

        return hasDigit && hasLetter;
    }



}
