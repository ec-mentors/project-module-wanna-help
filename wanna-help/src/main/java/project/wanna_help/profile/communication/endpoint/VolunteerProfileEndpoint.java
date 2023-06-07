package project.wanna_help.profile.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.logic.VolunteerProfileService;

@RestController
@RequestMapping("/profile/volunteer")
@Secured("ROLE_VOLUNTEER")
public class VolunteerProfileEndpoint {

    private final VolunteerProfileService volunteerProfileService;

    public VolunteerProfileEndpoint(VolunteerProfileService volunteerProfileService) {
        this.volunteerProfileService = volunteerProfileService;
    }

    @GetMapping("/myData")
    AppUserDTO viewData() {
        return volunteerProfileService.getAppUserData();
    }


    @PutMapping("/myData")
    AppUserDTO addData(@RequestBody AppUserDTO appUserDTO) {
        return volunteerProfileService.updateAppUserData(appUserDTO);
    }

    @GetMapping("/mySkills")
    String getSkills(){
        return volunteerProfileService.getVolunteerSkills();
    }

    @PostMapping("/mySkills")
    String updateSkills(@RequestBody String skills){
       return volunteerProfileService.updateVolunteerSkills(skills);
    }
    @GetMapping("/{id}")
    //TODO: profileendpoint with other roles!
    VolunteerDTO getVolunteerProfile(@PathVariable Long id){
        return volunteerProfileService.getVolunteerProfileSeenByOthers(id);
    }




}
