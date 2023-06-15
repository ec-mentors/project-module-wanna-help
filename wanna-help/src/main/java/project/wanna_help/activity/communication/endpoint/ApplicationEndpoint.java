package project.wanna_help.activity.communication.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.wanna_help.activity.logic.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationEndpoint {
    private final ApplicationService applicationService;

    public ApplicationEndpoint(ApplicationService applicationService) {
        this.applicationService = applicationService;
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
