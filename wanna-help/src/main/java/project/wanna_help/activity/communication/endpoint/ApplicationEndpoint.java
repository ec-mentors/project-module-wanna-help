package project.wanna_help.activity.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.activity.logic.ApplicationService;
import project.wanna_help.activity.persistence.domain.Application;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationEndpoint {
    private final ApplicationService applicationService;

    public ApplicationEndpoint(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    List<Application> helpSeekerViewApplication(){
        return applicationService.helpSeekerViewAllApplications();
    }

    @PutMapping("/{id}/accept")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    String acceptActivity(@PathVariable Long id) {
        return applicationService.acceptThisActivity(id);
    }

    @PutMapping("/{activityId}/done")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    String markActivityDone(@PathVariable Long activityId) {
        return applicationService.markActivityDone(activityId);
    }


    @PutMapping("/{id}/decline")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    String declineApplication(@PathVariable Long id) {
       return applicationService.declineApplication(id);
    }


}
