package project.wanna_help.logic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.repository.AppUserRepository;

import javax.validation.Valid;

@Service
public class RegistrationService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(@Valid AppUser appUser) {
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        return appUserRepository.save(appUser);

    }

}
