package project.wanna_help.Activity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityEndpoint {

    private final ActivityService activityService;


    public ActivityEndpoint(ActivityService activityService) {
        this.activityService = activityService;
    }
    @GetMapping("/archive/published")
    List<Activity> viewPublished() {
        return activityService.viewPublishedActivities();
    }
    @GetMapping("/archive/inprogress")
    List<Activity> viewInProgress() {
        return activityService.viewInProgressActivities();
    }

    @PostMapping("/add")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    Activity addActivity(@Valid @RequestBody Activity activity) {
        return activityService.addNewActivity(activity);
    }

    @PutMapping("/{activityId}/apply")
    @Secured("ROLE_VOLUNTEER")
    void applyingForActivity(@PathVariable Long id, @RequestBody ApplicationDto applicationDto) {
        activityService.applyForActivity(id, applicationDto);
    }

    @PutMapping("/{activityId}/cancel")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    void cancelPendingActivity(@PathVariable Long id, @RequestBody ApplicationDto applicationDto) {
        activityService.cancelPendingActivity(id, applicationDto);
    }

    @GetMapping("/{activityId}/manage")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    Activity displayActivity(@PathVariable Long id) {
        return activityService.displayThisActivity(id);
    }

    @PutMapping("/{activityId}/manage")
    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
    void updateActivity(@Valid @RequestBody Activity activity, @PathVariable String id) {

    }
}
