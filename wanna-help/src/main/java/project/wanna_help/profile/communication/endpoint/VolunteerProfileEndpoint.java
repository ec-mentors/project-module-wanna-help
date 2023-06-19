package project.wanna_help.profile.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.logic.ProfileService;
import project.wanna_help.notifications.NotificationDTO;
import project.wanna_help.notifications.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/profile/volunteer")
@Secured("ROLE_VOLUNTEER")
public class VolunteerProfileEndpoint {

    private final ProfileService profileService;
    private final NotificationService notificationService;


    public VolunteerProfileEndpoint(ProfileService profileService, NotificationService notificationService) {
        this.profileService = profileService;
        this.notificationService = notificationService;
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

    @PutMapping("/change_visibility")
    String changeProfileVisibility() {
        return profileService.changeProfileVisibility();
    }


    @GetMapping("/notifications")
    List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotification();
    }

}
