package project.wanna_help.activity.communication.endpoint;

import project.wanna_help.activity.logic.ApplicationService;

public class ApplicationEndpoint {
    private final ApplicationService applicationService;

    public ApplicationEndpoint(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
}
