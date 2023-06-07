package project.wanna_help.profile.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.logic.ProfileService;

@RestController
@RequestMapping("/profile/volunteer")
@Secured("ROLE_VOLUNTEER")
public class VolunteerProfileEndpoint {

    private final ProfileService profileService;

    public VolunteerProfileEndpoint(ProfileService profileService) {
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

    @GetMapping("/mySkills")
    String getSkills(){
        return profileService.getVolunteerSkills();
    }

    @PostMapping("/mySkills")
    String updateSkills(@RequestBody String skills){
       return profileService.updateVolunteerSkills(skills);
    }





}
