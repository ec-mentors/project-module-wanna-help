package project.wanna_help.logic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.repository.AppUserRepository;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.validation.Valid;

@Service
@Validated
public class RegistrationService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final VolunteerRepository volunteerRepository;

    public RegistrationService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, VolunteerRepository volunteerRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.volunteerRepository = volunteerRepository;
    }

    public AppUser register(@Valid AppUser appUser) {
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        switch(appUser.getRole()){
            case VOLUNTEER: volunteerRepository.save(new Volunteer(appUser)); break;
            case INDIVIDUAL: break; //TODO add individual creation
            case ORGANIZATION: break; //TODO add organization
        }
        return appUserRepository.save(appUser);

    }

}
