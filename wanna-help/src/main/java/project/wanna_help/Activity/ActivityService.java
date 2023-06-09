package project.wanna_help.Activity;

import org.springframework.stereotype.Service;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivitiesRepository activitiesRepository;
    private final VolunteerRepository volunteerRepository;

    private final HelpSeekerRepository helpSeekerRepository;


    public ActivityService(ActivitiesRepository activitiesRepository, VolunteerRepository volunteerRepository, HelpSeekerRepository helpSeekerRepository) {
        this.activitiesRepository = activitiesRepository;
        this.volunteerRepository = volunteerRepository;
        this.helpSeekerRepository = helpSeekerRepository;
    }

    public List<Activity> viewPublishedActivities() {
        return activitiesRepository.findByStatus(Status.PUBLISHED);
    }

    public List<Activity> viewInProgressActivities() {
        return activitiesRepository.findByStatus(Status.IN_PROGRESS);
    }

    public Activity addNewActivity(Activity activity) {
        activity.setStatus(Status.PUBLISHED);
        return activitiesRepository.save(activity);
    }

    public String applyForActivity(Long Id, ApplicationDto dto) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(dto.getVolunteerId());
        Optional<HelpSeeker> optionalHelpSeeker = helpSeekerRepository.findById(dto.getHelpSeekerId());
        if (optionalActivity.isEmpty() || optionalVolunteer.isEmpty() || optionalHelpSeeker.isEmpty()) {
            return "activity not found";
        }
        Activity selectedActivity = optionalActivity.get();
        Volunteer volunteer = optionalVolunteer.get();
        HelpSeeker helpSeeker = optionalHelpSeeker.get();
        selectedActivity.setStatus(Status.IN_PROGRESS);
        selectedActivity.setPending(true);
        volunteer.getApplications().add(selectedActivity);
        helpSeeker.getApplications().add(selectedActivity);
        volunteerRepository.save(volunteer);
        helpSeekerRepository.save(helpSeeker);
        return "applied successful";

    }

    public void cancelPendingActivity(Long Id, ApplicationDto dto) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if (optionalActivity.isEmpty()) {
            return;
        }
        Activity selectedActivity = optionalActivity.get();
        selectedActivity.setStatus(Status.PUBLISHED);
        //selectedActivity.setPending = false; // set pending boolean to true
        activitiesRepository.save(selectedActivity);

    }

    public Activity displayThisActivity(Long Id) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if (optionalActivity.isEmpty()) {
            return null;  // Possibly some message that this activity was not found could be displayed

        }
        Activity selectedActivity = optionalActivity.get();
        return selectedActivity;
    }


    public void displayThisActivity(Long Id, Activity activity) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if (optionalActivity.isPresent()) {
            Activity selectedActivity = optionalActivity.get();
            selectedActivity.setTitle(activity.getTitle());
            selectedActivity.setDescription(activity.getDescription());
            selectedActivity.setRecommendedSkills(activity.getRecommendedSkills());
            selectedActivity.setStartDate(activity.getStartDate());
            selectedActivity.setEndDate(activity.getEndDate());
        }


    }

}
