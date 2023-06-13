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

    //volunteer overview all activities:
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
        Activity activity = oActivity.get();
        Application application = new Application();
        Volunteer volunteer = userHelper.getCurrentVolunteer();
        application.setVolunteer(volunteer);
        application.setActivity(activity);
        application.setApplicationStatus(ApplicationStatus.PENDING);
        application.setTimeStamp(LocalDateTime.now());
        application.getActivity().setActivityStatus(ActivityStatus.IN_PROGRESS);
        applicationRepository.save(application);
        return "applied successful";

    }

    //    public String applyForActivity(Long Id, ApplicationDto dto) {
//
//
//
//        Optional<Activity> optionalActivity = activityRepository.findById(Id);
//        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(dto.getVolunteerId());
//        Optional<HelpSeeker> optionalHelpSeeker = helpSeekerRepository.findById(dto.getHelpSeekerId());
//        if (optionalActivity.isEmpty() || optionalVolunteer.isEmpty() || optionalHelpSeeker.isEmpty()) {
//            return "activity not found";
//        }
//        Activity selectedActivity = optionalActivity.get();
//        Volunteer volunteer = optionalVolunteer.get();
//        HelpSeeker helpSeeker = optionalHelpSeeker.get();
//        selectedActivity.setStatus(ActivityStatus.IN_PROGRESS);
//        selectedActivity.setPending(true);
//        volunteer.getApplications().add(selectedActivity);
//        helpSeeker.getApplications().add(selectedActivity);
//        volunteerRepository.save(volunteer);
//        helpSeekerRepository.save(helpSeeker);
//        return "applied successful";
//
//    }
    //volunteer get applications in pending
    public List<Application> displayApplicationInProgress() {
        Volunteer currentVolunteer = userHelper.getCurrentVolunteer();
        return applicationRepository.findByVolunteerAndApplicationStatus(currentVolunteer, ApplicationStatus.PENDING);
    }

    //volunteer cancel application
    //ROLE_VOLUNTEER
    public String cancelPendingApplication(Long id, @Validated @NotBlank(message = "Please write the comment to cancel the activity successfully.") String comment) {
        Volunteer currentVolunteer = userHelper.getCurrentVolunteer();
        Optional<Application> oApplication = applicationRepository.findByIdAndVolunteerAndApplicationStatus(id, currentVolunteer, ApplicationStatus.PENDING);
        if (oApplication.isEmpty()) {
            throw new EntityNotFoundException("application not found");
        }
        Application application = oApplication.get();
        application.setApplicationStatus(ApplicationStatus.ABORTED);
        application.setComment(comment);
        applicationRepository.save(application);
        return "The activity was canceled successfully.";
    }

//    public String cancelPendingActivity(Long Id, ApplicationDto dto) {
//        Optional<Activity> optionalActivity = activityRepository.findById(Id);
//        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(dto.getVolunteerId());
//        Optional<HelpSeeker> optionalHelpSeeker = helpSeekerRepository.findById(dto.getHelpSeekerId());
//        if (optionalActivity.isEmpty() || optionalVolunteer.isEmpty() || optionalHelpSeeker.isEmpty()) {
//            return "activity not found";
//        }
//        if (dto.getComment().length() > 0) {
//            Activity selectedActivity = optionalActivity.get();
//            Volunteer volunteer = optionalVolunteer.get();
//            HelpSeeker helpSeeker = optionalHelpSeeker.get();
//            helpSeeker.getApplications().remove(selectedActivity);
//            selectedActivity.setAborted(true);
//            selectedActivity.setComment(dto.getComment());
//            selectedActivity.setTimeStamp(java.time.LocalDateTime.now());
//            volunteer.getApplications().remove(selectedActivity);
//            activityRepository.save(selectedActivity);
//            helpSeeker.getApplications().add(selectedActivity);
//            volunteerRepository.save(volunteer);
//            helpSeekerRepository.save(helpSeeker);
//            return "The activity was canceled successfully.";
//
//        } else {
//            return "Please write the comment to cancel the activity successfully.";
//        }
//
//    }

    //helpseeker overview his one in-progress activities -> secured Helpseeker
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

//        public void updateThisActivity(Activity activity, Long Id) {
//            Optional<Activity> optionalActivity = activityRepository.findById(Id);
//            if (optionalActivity.isPresent()) {
//                Activity selectedActivity = optionalActivity.get();
//                selectedActivity.setTitle(activity.getTitle());
//                selectedActivity.setDescription(activity.getDescription());
//                selectedActivity.setRecommendedSkills(activity.getRecommendedSkills());
//                selectedActivity.setStartDate(activity.getStartDate());
//                selectedActivity.setEndDate(activity.getEndDate());
//            }


    }

    public String acceptThisActivity(Long applicationId) {
        HelpSeeker currentHelpSeeker = userHelper.getCurrentHelpSeeker();
        Optional<Application> oApplication = applicationRepository.findByApplicationIdAndApplicationStatusAndActivity_HelpSeeker(applicationId, ApplicationStatus.PENDING, currentHelpSeeker);
        if (oApplication.isEmpty()) {
            throw new EntityNotFoundException("application not found");
        }
        Application application = oApplication.get();
        application.setApplicationStatus(ApplicationStatus.ENROLLED);
        applicationRepository.save(application);
        return "The activity was accepted successfully.";
    }
}
