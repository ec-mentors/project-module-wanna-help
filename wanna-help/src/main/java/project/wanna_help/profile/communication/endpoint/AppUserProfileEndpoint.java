package project.wanna_help.profile.communication.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.HelpSeekerDTO;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.logic.ProfileService;
import project.wanna_help.profile.persistence.domain.Rating;

import java.util.List;


@RestController
@RequestMapping("/profile")
public class AppUserProfileEndpoint {

    private final ProfileService profileService;

    public AppUserProfileEndpoint(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Secured({"ROLE_VOLUNTEER", "ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @GetMapping("/volunteer/{id}")
    VolunteerDTO getVolunteerProfile(@PathVariable Long id) {
        return profileService.getVolunteerProfileSeenByOthers(id);
    }

    @Secured({"ROLE_VOLUNTEER", "ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @GetMapping("/helpSeeker/{id}")
    HelpSeekerDTO getHelpSeekerProfile(@PathVariable Long id) {
        return profileService.getHelpSeekerProfileSeenByOthers(id);
    }

    @Secured("ROLE_VOLUNTEER")
    @PostMapping("/helpSeeker/{id}/rating")
    void postRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        profileService.saveRating(id, ratingDTO);
    }

    @Secured({"ROLE_VOLUNTEER", "ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @GetMapping("/{id}/ratings")
    List<RatingDTO> getAllRating(@PathVariable Long id) {
        return profileService.getAllRatingsForHelpSeeker(id);
    }

}
