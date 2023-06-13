package project.wanna_help.activity.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.ActivityStatus;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.activity.persistence.domain.ApplicationStatus;
import project.wanna_help.activity.persistence.repository.ActivityRepository;
import project.wanna_help.activity.persistence.repository.ApplicationRepository;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.profile.persistence.domain.HelpSeeker;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ActivityRepository activityRepository;

    private final UserHelper userHelper;

    public ApplicationService(ApplicationRepository applicationRepository, ActivityRepository activityRepository, UserHelper userHelper) {
        this.applicationRepository = applicationRepository;
        this.activityRepository = activityRepository;
        this.userHelper = userHelper;
    }

    public String acceptThisActivity(Long applicationId) {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        Optional<Application> oApplication = applicationRepository.findByIdAndApplicationStatusAndActivity_HelpSeeker(applicationId, ApplicationStatus.PENDING, currentHelpSeeker);
        if (oApplication.isEmpty()) {
            throw new EntityNotFoundException("application not found");
        }
        Application application = oApplication.get();
        application.setApplicationStatus(ApplicationStatus.ENROLLED);
        applicationRepository.save(application);
        return "The activity was accepted successfully.";
    }

    public String declineApplication(Long applicationId) {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        Optional<Application> oApplication =  applicationRepository.findByIdAndApplicationStatusAndActivity_HelpSeeker(applicationId, ApplicationStatus.PENDING, currentHelpSeeker);
        if (oApplication.isEmpty()) {
            throw new EntityNotFoundException("application not found");
        }
        Activity activity = oApplication.get().getActivity();
        activity.setActivityStatus(ActivityStatus.ARCHIVE);
        oApplication.get().setApplicationStatus(ApplicationStatus.DECLINED);
        activityRepository.save(activity);
        applicationRepository.save(oApplication.get());
        return "application declined";
    }

}
