package project.wanna_help.Activity;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivitiesRepository activitiesRepository;


    public ActivityService(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }

    public List<Activity> viewPublishedActivities() {
        return activitiesRepository.findByStatus(Status.PUBLISHED);
    }

    public List<Activity> viewInProgressActivities() {
        return activitiesRepository.findByStatus(Status.IN_PROGRESS);
    }

    public Activity addNewActivity(Activity activity) {
        return activitiesRepository.save(activity);
    }

    public void applyForActivity(Long Id) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if(optionalActivity.isEmpty()) {
            return;
        }
        Activity selectedActivity = optionalActivity.get();
        selectedActivity.setStatus(Status.IN_PROGRESS);
            activitiesRepository.save(selectedActivity);

    }

    public void cancelPendingActivity(Long Id) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if(optionalActivity.isEmpty()) {
            return;
        }
            Activity selectedActivity = optionalActivity.get();
            selectedActivity.setStatus(Status.PUBLISHED);
            //selectedActivity.setPending = false; // set pending boolean to true
            activitiesRepository.save(selectedActivity);

    }

    public Activity displayThisActivity(Long Id) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if(optionalActivity.isEmpty()) {
            return null;  // Possibly some message that this activity was not found could be displayed

        }
            Activity selectedActivity = optionalActivity.get();
            return selectedActivity;
        }


    public void displayThisActivity(Long Id, Activity activity) {
        Optional<Activity> optionalActivity = activitiesRepository.findById(Id);
        if(optionalActivity.isPresent()) {
            Activity selectedActivity = optionalActivity.get();
            selectedActivity.setTitle(activity.getTitle());
            selectedActivity.setDescription(activity.getDescription());
            selectedActivity.setRecommendedSkills(activity.getRecommendedSkills());
            selectedActivity.setStartDate(activity.getStartDate());
            selectedActivity.setEndDate(activity.getEndDate());
        }

    }
}
