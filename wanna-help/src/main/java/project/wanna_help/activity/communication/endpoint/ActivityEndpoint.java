package project.wanna_help.activity.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.activity.logic.ActivityService;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.Application;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityEndpoint {

    private final ActivityService activityService;

    public ActivityEndpoint(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/add")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    Activity addActivity(@Valid @RequestBody Activity activity) {
        return activityService.addNewActivity(activity);
    }

    @GetMapping("/helpSeeker/published")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    List<Activity> helpSeekerViewOwnPublished() {
        return activityService.helpSeekerViewPublishedActivities();
    }

    @GetMapping("/volunteer/published")
    @Secured("ROLE_VOLUNTEER")
    List<Activity> volunteerViewAllPublishedActivities() {
        return activityService.volunteerViewPublishedActivities();
    }

    @GetMapping("/{activityId}/manage")
    @Secured("ROLE_VOLUNTEER")
    Activity displayActivity(@PathVariable Long activityId) {
        return activityService.displayThisActivity(activityId);
    }

    @PutMapping("/{id}/apply")
    @Secured("ROLE_VOLUNTEER")
    String applyingForActivity(@PathVariable Long id) {
        return activityService.applyForActivity(id);
    }

    @GetMapping("/volunteer/inProgress")
    @Secured("ROLE_VOLUNTEER")
    List<Application> volunteerViewAllApplicationInProgress() {
        return activityService.displayApplicationInProgress();
    }

    @PutMapping("/{id}/cancel")
    @Secured("ROLE_VOLUNTEER")
    String cancelPendingActivity(@PathVariable Long id, @Valid @NotBlank(message = "Please write the comment to cancel the activity successfully.") @RequestBody String comment) {
        return activityService.cancelPendingApplication(id, comment);
    }

    @GetMapping("/helpSeeker/inProgress")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    List<Activity> helpSeekerViewInProgress() {
        return activityService.viewInProgressActivities();
    }

    @GetMapping("/helpSeeker/archive")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    List<Activity> helpSeekerViewArchive() {
        return activityService.helpSeekerOverViewArchive();
    }

    @PutMapping("/{activityId}/manage")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    void updateActivity(@PathVariable Long activityId, @Valid @RequestBody Activity updatedActivity) {
        activityService.updateThisActivity(activityId, updatedActivity);
    }

    @GetMapping("/volunteer/archive")
    @Secured("ROLE_VOLUNTEER")
    List<Application> volunteerViewAllApplicationInArchive() {
        return activityService.volunteerViewArchiveActivities();
    }


}