package project.wanna_help.registration.logic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.wanna_help.registration.persistence.domain.AppUser;
import project.wanna_help.registration.persistence.repository.AppUserRepository;

import java.util.Optional;

@Service
public class LoginService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private int attemptCount = 0;

    public LoginService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> login(String usernameOrEmail, String password) {
        return appUserRepository.findOneByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .filter(appUser -> passwordEncoder.matches(password, appUser.getPassword()));

    }

}
