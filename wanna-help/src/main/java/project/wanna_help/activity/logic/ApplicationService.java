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
import project.wanna_help.profile.persistence.domain.Volunteer;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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
        Optional<Application> oApplication = applicationRepository
                .findByIdAndApplicationStatusAndActivity_HelpSeeker(applicationId, ApplicationStatus.PENDING, currentHelpSeeker);
        if (oApplication.isEmpty()) {
            throw new EntityNotFoundException("application not found");
        }
        Application application = oApplication.get();
        application.setApplicationStatus(ApplicationStatus.ENROLLED);
        application.getActivity().setActivityStatus(ActivityStatus.IN_PROGRESS);
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
        oApplication.get().setApplicationStatus(ApplicationStatus.DECLINED);
        activityRepository.save(activity);
        applicationRepository.save(oApplication.get());
        return "application declined";
    }


    public String markActivityDone(Long activityId) {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        Optional<Activity> optionalActivity = activityRepository.findByIdAndHelpSeeker(activityId, currentHelpSeeker);

        if (optionalActivity.isEmpty()) {
            throw new EntityNotFoundException("activity was not found");
        }
        Activity activity = optionalActivity.get();
        List<Application> applications = applicationRepository.findByActivity(activity);
        boolean allApplicationsEnrolledOrDeclined = applications.stream()
                .map(Application::getApplicationStatus)
                .allMatch(applicationStatus -> applicationStatus == ApplicationStatus.ENROLLED
                        || applicationStatus == ApplicationStatus.DECLINED);
        boolean activityInProgress = activity.getActivityStatus() == ActivityStatus.IN_PROGRESS;

        if (allApplicationsEnrolledOrDeclined && activityInProgress) {
            activity.setActivityStatus(ActivityStatus.ARCHIVE);
            applications.stream()
                    .filter(application -> application.getApplicationStatus() == ApplicationStatus.ENROLLED)
                    .forEach(application -> application.setApplicationStatus(ApplicationStatus.DONE));
            activityRepository.save(activity);
        } else {
            throw new IllegalArgumentException("Can not mark the activity as Done");

        }
        return "Activity was marked to DONE";
    }

    public List<Application> helpSeekerViewAllApplications(){
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        return applicationRepository.findByApplicationStatusAndActivity_HelpSeeker(ApplicationStatus.PENDING,currentHelpSeeker);
    }
}
