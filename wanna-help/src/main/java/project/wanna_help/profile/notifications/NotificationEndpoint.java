package project.wanna_help.profile.notifications;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile/organization-individual")
public class NotificationEndpoint {

    private final NotificationService notificationService;

    public NotificationEndpoint(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Secured({"ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
    @PostMapping("/{volunteerId}/notify")
    String sendNotification(@PathVariable Long volunteerId,
                            @RequestParam Long activityId,
                            @RequestBody String notificationMessage) {
        return notificationService.sendNotification(volunteerId, activityId, notificationMessage);
    }


}
