package project.wanna_help.profile.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.logic.ProfileService;
import project.wanna_help.profile.persistence.domain.Rating;

import java.util.List;

@RestController
@RequestMapping("profile/organization-individual")
@Secured({"ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
public class HelpSeekerProfileEndpoint {

    private final ProfileService profileService;

    public HelpSeekerProfileEndpoint(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/myData")
    AppUserDTO viewData() {
        return profileService.getAppUserData();
    }


    @PutMapping("/myData")
    AppUserDTO addData(@RequestBody AppUserDTO appUserDTO) {
        return profileService.updateAppUserData(appUserDTO);
    }



}
