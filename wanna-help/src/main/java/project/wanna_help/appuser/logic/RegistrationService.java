package project.wanna_help.appuser.logic;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.appuser.persistence.repository.AppUserRepository;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.validation.Valid;

@Service
@Validated
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final VolunteerRepository volunteerRepository;

    private final HelpSeekerRepository helpSeekerRepository;

    public RegistrationService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, VolunteerRepository volunteerRepository, HelpSeekerRepository helpSeekerRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.volunteerRepository = volunteerRepository;
        this.helpSeekerRepository = helpSeekerRepository;
    }

    public AppUser register(@Valid AppUser appUser) {
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUser = appUserRepository.save(appUser);
        switch (appUser.getRole()) {
            case VOLUNTEER:
                volunteerRepository.save(new Volunteer(appUser));
                break;
            case INDIVIDUAL:
            case ORGANIZATION:
                helpSeekerRepository.save(new HelpSeeker(appUser));
                break;

        }
        return appUser;

    }

}
