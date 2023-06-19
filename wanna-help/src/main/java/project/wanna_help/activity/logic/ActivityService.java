package project.wanna_help.activity.logic;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
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
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ApplicationRepository applicationRepository;

    private final UserHelper userHelper;

    public ActivityService(ActivityRepository activityRepository, ApplicationRepository applicationRepository, UserHelper userHelper) {
        this.activityRepository = activityRepository;
        this.applicationRepository = applicationRepository;
        this.userHelper = userHelper;
    }

    //add activity + publish
    //helpsseker add activity ->secured helpseeker
    public Activity addNewActivity(Activity activity) {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        activity.setHelpSeeker(currentHelpSeeker);
        activity.setActivityStatus(ActivityStatus.PUBLISHED);
        return activityRepository.save(activity);
    }

    //helpseeker overview his own published activities -> secured Helpseeker
    public List<Activity> helpSeekerViewPublishedActivities() {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        return activityRepository.findByActivityStatusAndHelpSeeker(ActivityStatus.PUBLISHED, currentHelpSeeker);
    }

    //volunteer overview published activities:
    public List<Activity> volunteerViewPublishedActivities() {
        return activityRepository.findByActivityStatus(ActivityStatus.PUBLISHED);
    }


    //volunteer display specific activity
    public Activity displayThisActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isEmpty()) {
            return null;  // Possibly some message that this activity was not found could be displayed

        }
        return optionalActivity.get();
    }

    //Volunteer applies for activity
    public String applyForActivity(Long id) {
        Optional<Activity> oActivity = activityRepository.findByIdAndActivityStatus(id, ActivityStatus.PUBLISHED);
        if (oActivity.isEmpty()) {
            throw new EntityNotFoundException("no activity found");
        }
        Volunteer volunteer = userHelper.getCurrentVolunteer();
        Activity activity = oActivity.get();
        List<Application> pendingDoneAndDeclinedApplications = applicationRepository
                .findByVolunteerAndActivityAndApplicationStatusIn(
                        volunteer,
                        activity,
                        List.of(ApplicationStatus.PENDING, ApplicationStatus.DECLINED, ApplicationStatus.DONE));
        if (!pendingDoneAndDeclinedApplications.isEmpty()) {
            throw new IllegalArgumentException("You can't apply again");
        }
        Application application = new Application();
        application.setVolunteer(volunteer);
        application.setActivity(activity);
        application.setApplicationStatus(ApplicationStatus.PENDING);
        application.setTimeStamp(LocalDateTime.now());
        applicationRepository.save(application);
        return "applied successful";

    }

    //volunteer get applications in pending and enrolled (Application in PROGRESS)
    public List<Application> displayApplicationInProgress() {
        Volunteer currentVolunteer = userHelper.getCurrentVolunteer();
        return applicationRepository.findByVolunteerAndApplicationStatusIn(currentVolunteer,
                List.of(ApplicationStatus.PENDING, ApplicationStatus.ENROLLED));
    }

    //volunteer cancel application
    public String cancelPendingApplication(Long id, String comment) {
        Volunteer currentVolunteer = userHelper.getCurrentVolunteer();
        Optional<Application> oApplication = applicationRepository.findByIdAndVolunteerAndApplicationStatus(id, currentVolunteer, ApplicationStatus.PENDING);
        if (oApplication.isEmpty()) {
            throw new EntityNotFoundException("application not found");
        }
        Application application = oApplication.get();
        application.setApplicationStatus(ApplicationStatus.ABORTED);
        applicationRepository.save(application);
        return "The activity was canceled successfully.";
    }


    //helpseeker overview his one in-progress activities
    public List<Activity> viewInProgressActivities() {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        return activityRepository.findByActivityStatusAndHelpSeeker(ActivityStatus.IN_PROGRESS, currentHelpSeeker);
    }


    //helpseeker update activity  -> secured Helpseeker
    public void updateThisActivity(Long id, Activity updatedActivity) {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        Optional<Activity> optionalActivity = activityRepository.findByIdAndHelpSeeker(id, currentHelpSeeker);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activity.setTitle(updatedActivity.getTitle());
            activity.setDescription(updatedActivity.getDescription());
            activity.setRecommendedSkills(updatedActivity.getRecommendedSkills());
            activity.setStartDate(updatedActivity.getStartDate());
            activity.setEndDate(updatedActivity.getEndDate());
        }
    }

    //helpsseker overview his activities in archive

    public List<Activity> helpSeekerOverViewArchive() {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        return activityRepository.findByActivityStatusAndHelpSeeker(ActivityStatus.ARCHIVE, currentHelpSeeker);

    }

    //volunteer view his application in archive (declined done aborted)
    public List<Application> volunteerViewArchiveActivities() {
        Volunteer currentVolunteer = userHelper.getCurrentVolunteer();
        return applicationRepository.findByVolunteerAndApplicationStatusIn(currentVolunteer,
                List.of(ApplicationStatus.DECLINED, ApplicationStatus.DONE, ApplicationStatus.ABORTED));
    }


}
