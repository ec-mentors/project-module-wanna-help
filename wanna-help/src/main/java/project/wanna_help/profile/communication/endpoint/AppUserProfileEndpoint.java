package project.wanna_help.profile.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.wanna_help.profile.communication.dto.HelpSeekerDTO;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.logic.ProfileService;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.List;

@RestController
@RequestMapping("/profile/")
@Secured({"ROLE_VOLUNTEER", "ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
public class AppUserProfileEndpoint {

    private final ProfileService profileService;

    public AppUserProfileEndpoint(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/volunteer/{id}")
    VolunteerDTO getVolunteerProfile(@PathVariable Long id) {
        return profileService.getVolunteerProfileSeenByOthers(id);
    }

    @GetMapping("/helpSeeker/{id}")
    HelpSeekerDTO getHelpSeekerProfile(@PathVariable Long id) {
        return profileService.getHelpSeekerProfileSeenByOthers(id);
    }

    @GetMapping("/available_volunteers")
    List<VolunteerDTO> getAllAvailableVolunteers() {
        return profileService.getAllAvailableVolunteers();
    }
}
