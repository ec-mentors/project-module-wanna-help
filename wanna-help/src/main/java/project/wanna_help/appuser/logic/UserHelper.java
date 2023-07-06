package project.wanna_help.appuser.logic;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.appuser.security.UserPrincipal;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserHelper {

    private final VolunteerRepository volunteerRepository;
    private final HelpSeekerRepository helpSeekerRepository;

    public UserHelper(VolunteerRepository volunteerRepository, HelpSeekerRepository helpSeekerRepository) {
        this.volunteerRepository = volunteerRepository;
        this.helpSeekerRepository = helpSeekerRepository;
    }


    /**
     * get current User from the cookies, after Signin, now it's possible to
     * get Data depending from the currently locked-in User and it's privileges
     *
     * @return AppUser
     */
    public AppUser getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getAppUser();
    }

    public Volunteer getCurrentVolunteer() {
        AppUser currentUser = getCurrentUser();
        Optional<Volunteer> oVolunteer = volunteerRepository.findByAppUser(currentUser);
        if (oVolunteer.isPresent()) {
            return oVolunteer.get();
        }
        throw new EntityNotFoundException();
    }

    public HelpSeeker getCurrentHelpSeeker() {
        AppUser currentUser = getCurrentUser();
        Optional<HelpSeeker> oHelpSeeker = helpSeekerRepository.findByAppUser(currentUser);
        if (oHelpSeeker.isPresent()) {
            return oHelpSeeker.get();
        }
        throw new EntityNotFoundException();
    }
}
