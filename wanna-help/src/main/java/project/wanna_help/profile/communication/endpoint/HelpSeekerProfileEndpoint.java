package project.wanna_help.profile.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.logic.ProfileService;
import project.wanna_help.profile.logic.RatingService;

import java.util.List;

@RestController
@RequestMapping("profile/organization-individual")
public class HelpSeekerProfileEndpoint {

    private final ProfileService profileService;
    private final RatingService ratingService;

    public HelpSeekerProfileEndpoint(ProfileService profileService, RatingService ratingService) {
        this.profileService = profileService;
        this.ratingService = ratingService;
    }

    @Secured({"ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @GetMapping("/myData")
    AppUserDTO viewData() {
        return profileService.getAppUserData();
    }


    @Secured({"ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @PutMapping("/myData")
    AppUserDTO addData(@RequestBody AppUserDTO appUserDTO) {
        return profileService.updateAppUserData(appUserDTO);
    }

    @Secured("ROLE_VOLUNTEER")
    @PostMapping("/{id}/rating")
    void postRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        ratingService.saveRating(id, ratingDTO);
    }

    @Secured({"ROLE_VOLUNTEER", "ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @GetMapping("/{id}/ratings")
    List<RatingDTO> getAllRating(@PathVariable Long id) {
        return ratingService.getAllRatingsForHelpSeeker(id);
    }


}
