package project.wanna_help.activity.communication.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.wanna_help.activity.logic.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityEndpoint {

    private final ActivityService activityService;

    public ActivityEndpoint(ActivityService activityService) {
        this.activityService = activityService;
    }

    //TODO: update Endpoints

//    @GetMapping("/archive/published")
//    List<Activity> viewPublished() {
//        return activityService.helpSeekerViewPublishedActivities();
//    }
//    @GetMapping("/archive/inprogress")
//    List<Activity> viewInProgress() {
//        return activityService.viewInProgressActivities();
//    }
//
//    @PostMapping("/add")
//    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
//    Activity addActivity(@Valid @RequestBody Activity activity) {
//        return activityService.addNewActivity(activity);
//    }
//
//    @PutMapping("/{id}/apply")
//    @Secured("ROLE_VOLUNTEER")
//    String applyingForActivity(@PathVariable Long id, @RequestBody ApplicationDto applicationDto) {
//        return activityService.applyForActivity(id, applicationDto);
//    }
//
//    @PutMapping("/{id}/cancel")
//    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
//    String cancelPendingActivity(@PathVariable Long id, @RequestBody ApplicationDto applicationDto) {
//        return activityService.cancelPendingActivity(id, applicationDto);
//    }
//
//    @GetMapping("/{activityId}/manage")
//    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
//    Activity displayActivity(@PathVariable Long activityId) {
//        return activityService.displayThisActivity(activityId);
//    }
//
//    @PutMapping("/{activityId}/manage")
//    @Secured({"ROLE_ORGANIZATION","ROLE_INDIVIDUAL"})
//    void updateActivity(@Valid @RequestBody Activity activity, @PathVariable Long activityId) {
//        activityService.updateThisActivity(activity, activityId);
//    }

}