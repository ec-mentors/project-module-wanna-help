package project.wanna_help.notifications;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{volunteerId}/accept_invitation")
    String acceptInvitation(@PathVariable Long volunteerId,
                            @RequestParam Long activityId) {
        notificationService.acceptInvitationStatus(volunteerId, activityId);
        return "Thank you for accepting our invitation";
    }

 @GetMapping("/{volunteerId}/decline_invitation")
    String declineInvitation(@PathVariable Long volunteerId,
                            @RequestParam Long activityId) {
        notificationService.declineInvitationStatus(volunteerId, activityId);
        return "You reject the invitation";
    }



}
