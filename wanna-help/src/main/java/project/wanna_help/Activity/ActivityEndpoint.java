package project.wanna_help.Activity;

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
    Activity addActivity(@Valid @RequestBody Activity activity) {
        return activityService.addNewActivity(activity);
    }

    @PutMapping("/{activityId}/apply")
    void applyingForActivity(@PathVariable Long id) {
        activityService.applyForActivity(id);
    }

    @PutMapping("/{activityId}/cancel")
    void cancelPendingActivity(@PathVariable Long id) {
        activityService.applyForActivity(id);
    }

    @GetMapping("/{activityId}/manage")
    Activity displayActivity(@PathVariable Long id) {
        return activityService.displayThisActivity(id);
    }

    @PutMapping("/{activityId}/manage")
    void updateActivity(@Valid @RequestBody Activity activity, @PathVariable String id) {

    }


}
