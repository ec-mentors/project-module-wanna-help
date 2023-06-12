package project.wanna_help.activity.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.activity.logic.ActivityService;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.Application;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityEndpoint {

    private final ActivityService activityService;

    public ActivityEndpoint(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/add")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    Activity addActivity(@Valid @RequestBody Activity activity) {
        return activityService.addNewActivity(activity);
    }
    @GetMapping("/archive/helpSeeker/published")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    List<Activity> helpSeekerViewOwnPublished() {
        return activityService.helpSeekerViewPublishedActivities();
    }

    @GetMapping("/archive/published")
    @Secured("ROLE_VOLUNTEER")
    List<Activity> volunteerViewAllPublishedActivities(){
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

    @GetMapping("/archive/volunteer/inProgress")
    @Secured("ROLE_VOLUNTEER")
    List<Application> volunteerViewAllApplicationInProgress(){
        return activityService.displayApplicationInProgress();
    }

    @PutMapping("/{id}/cancel")
    @Secured("ROLE_VOLUNTEER")
    String cancelPendingActivity(@PathVariable Long id, @RequestBody String comment) {
        return activityService.cancelPendingApplication(id, comment);
    }
    @GetMapping("/archive/helpSeeker/inprogress")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    List<Activity> viewInProgress() {
        return activityService.viewInProgressActivities();
    }

    @PutMapping("/{activityId}/manage")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    void updateActivity(@PathVariable Long activityId,@Valid @RequestBody Activity updatedActivity) {
        activityService.updateThisActivity(activityId,updatedActivity);
    }


}