package project.wanna_help.activity.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.SecurityFilterChain;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.ActivityStatus;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.activity.persistence.domain.ApplicationStatus;
import project.wanna_help.activity.persistence.repository.ActivityRepository;
import project.wanna_help.activity.persistence.repository.ApplicationRepository;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ActivityServiceTest {

    @Autowired
    ActivityService activityService;
    @MockBean
    ActivityRepository activityRepository;
    @MockBean
    ApplicationRepository applicationRepository;
    @MockBean
    UserHelper userHelper;
    @MockBean
    SecurityFilterChain securityFilterChain;


    @Test
    void addNewActivity() {
        HelpSeeker helpSeeker = new HelpSeeker();
        Activity activity = new Activity();

        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Mockito.when(activityRepository.save(activity)).thenReturn(activity);

        Activity result = activityService.addNewActivity(activity);

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(activityRepository).save(activity);

        assertEquals(ActivityStatus.PUBLISHED, result.getActivityStatus());
        assertEquals(helpSeeker, result.getHelpSeeker());
    }


    @Test
    void helpSeekerViewPublishedActivities() {
        HelpSeeker helpSeeker = new HelpSeeker();
        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);

        List<Activity> expectedActivities = new ArrayList<>();
        Activity activity1 = new Activity();
        activity1.setActivityStatus(ActivityStatus.PUBLISHED);
        Activity activity2 = new Activity();
        activity2.setActivityStatus(ActivityStatus.PUBLISHED);
        expectedActivities.add(activity1);
        expectedActivities.add(activity2);

        Mockito.when(activityRepository.findByActivityStatusAndHelpSeeker(ActivityStatus.PUBLISHED, helpSeeker))
                .thenReturn(expectedActivities);

        List<Activity> result = activityService.helpSeekerViewPublishedActivities();

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(activityRepository).findByActivityStatusAndHelpSeeker(ActivityStatus.PUBLISHED, helpSeeker);

        assertEquals(expectedActivities, result);
    }


    @Test
    void volunteerViewPublishedActivities() {
        List<Activity> expectedActivities = new ArrayList<>();
        Activity activity1 = new Activity();
        activity1.setActivityStatus(ActivityStatus.PUBLISHED);
        Activity activity2 = new Activity();
        activity2.setActivityStatus(ActivityStatus.PUBLISHED);
        expectedActivities.add(activity1);
        expectedActivities.add(activity2);

        Mockito.when(activityRepository.findByActivityStatus(ActivityStatus.PUBLISHED))
                .thenReturn(expectedActivities);

        List<Activity> result = activityService.volunteerViewPublishedActivities();

        Mockito.verify(activityRepository).findByActivityStatus(ActivityStatus.PUBLISHED);

        assertEquals(expectedActivities, result);
    }

    @Test
    void displayThisActivity() {
        Long activityId = 1L;
        Activity expectedActivity = new Activity();
        Mockito.when(activityRepository.findById(activityId)).thenReturn(Optional.of(expectedActivity));

        Activity result = activityService.displayThisActivity(activityId);

        Mockito.verify(activityRepository).findById(activityId);

        assertEquals(expectedActivity, result);

    }

    @Test
    void applyForActivity() {
        Long activityId = 1L;
        Volunteer volunteer = new Volunteer();
        Activity activity = new Activity();

        Mockito.when(activityRepository.findByIdAndActivityStatus(activityId, ActivityStatus.PUBLISHED))
                .thenReturn(Optional.of(activity));
        Mockito.when(userHelper.getCurrentVolunteer()).thenReturn(volunteer);
        Mockito.when(applicationRepository.findByVolunteerAndActivityAndApplicationStatusIn(
                        volunteer,
                        activity,
                        List.of(ApplicationStatus.PENDING, ApplicationStatus.DECLINED, ApplicationStatus.DONE)))
                .thenReturn(Collections.emptyList());

        String result = activityService.applyForActivity(activityId);

        Mockito.verify(activityRepository).findByIdAndActivityStatus(activityId, ActivityStatus.PUBLISHED);
        Mockito.verify(userHelper).getCurrentVolunteer();
        Mockito.verify(applicationRepository).findByVolunteerAndActivityAndApplicationStatusIn(
                volunteer,
                activity,
                List.of(ApplicationStatus.PENDING, ApplicationStatus.DECLINED, ApplicationStatus.DONE));
        Mockito.verify(applicationRepository).save(Mockito.any(Application.class));

        assertEquals("applied successful", result);

    }

    @Test
    void displayApplicationInProgress() {
        Volunteer volunteer = new Volunteer();
        List<Application> expectedApplications = new ArrayList<>();
        Application application1 = new Application();
        application1.setApplicationStatus(ApplicationStatus.PENDING);
        Application application2 = new Application();
        application2.setApplicationStatus(ApplicationStatus.ENROLLED);
        expectedApplications.add(application1);
        expectedApplications.add(application2);

        Mockito.when(userHelper.getCurrentVolunteer()).thenReturn(volunteer);
        Mockito.when(applicationRepository.findByVolunteerAndApplicationStatusIn(
                        volunteer,
                        List.of(ApplicationStatus.PENDING, ApplicationStatus.ENROLLED)))
                .thenReturn(expectedApplications);

        List<Application> result = activityService.displayApplicationInProgress();

        Mockito.verify(userHelper).getCurrentVolunteer();
        Mockito.verify(applicationRepository).findByVolunteerAndApplicationStatusIn(
                volunteer,
                List.of(ApplicationStatus.PENDING, ApplicationStatus.ENROLLED));

        assertEquals(expectedApplications, result);
    }


    @Test
    void cancelPendingApplication() {
        Long applicationId = 1L;
        Volunteer volunteer = new Volunteer();
        Application application = new Application();

        String comment = "Cancellation reason";
        Mockito.when(userHelper.getCurrentVolunteer()).thenReturn(volunteer);
        Mockito.when(applicationRepository.findByIdAndVolunteerAndApplicationStatus(
                        applicationId,
                        volunteer,
                        ApplicationStatus.PENDING))
                .thenReturn(Optional.of(application));

        String result = activityService.cancelPendingApplication(applicationId, comment);

        Mockito.verify(userHelper).getCurrentVolunteer();
        Mockito.verify(applicationRepository).findByIdAndVolunteerAndApplicationStatus(
                applicationId,
                volunteer,
                ApplicationStatus.PENDING);
        Mockito.verify(applicationRepository).save(Mockito.any(Application.class));

        assertEquals("The activity was canceled successfully.", result);
        assertEquals(ApplicationStatus.ABORTED, application.getApplicationStatus());
//        assertEquals(comment, application.getComment());


    }

    @Test
    void viewInProgressActivities() {
        HelpSeeker helpSeeker = new HelpSeeker();
        List<Activity> expectedActivities = new ArrayList<>();
        Activity activity1 = new Activity();
        activity1.setActivityStatus(ActivityStatus.IN_PROGRESS);
        Activity activity2 = new Activity();
        activity2.setActivityStatus(ActivityStatus.IN_PROGRESS);
        expectedActivities.add(activity1);
        expectedActivities.add(activity2);

        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Mockito.when(activityRepository.findByActivityStatusAndHelpSeeker(
                        ActivityStatus.IN_PROGRESS,
                        helpSeeker))
                .thenReturn(expectedActivities);

        List<Activity> result = activityService.viewInProgressActivities();

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(activityRepository).findByActivityStatusAndHelpSeeker(
                ActivityStatus.IN_PROGRESS,
                helpSeeker);

        assertEquals(expectedActivities, result);

    }

    @Test
    void updateThisActivity() {
        Long activityId = 1L;
        HelpSeeker helpSeeker = new HelpSeeker();
        Activity updatedActivity = new Activity();
        updatedActivity.setTitle("Updated Title");
        updatedActivity.setDescription("Updated Description");
        updatedActivity.setRecommendedSkills("Updated Skills");
        updatedActivity.setStartDate(LocalDate.of(2023, 6, 1));
        updatedActivity.setEndDate(LocalDate.of(2023, 6, 30));

        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);

        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Mockito.when(activityRepository.findByIdAndHelpSeeker(activityId, helpSeeker))
                .thenReturn(Optional.of(existingActivity));

        activityService.updateThisActivity(activityId, updatedActivity);

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(activityRepository).findByIdAndHelpSeeker(activityId, helpSeeker);

        assertEquals("Updated Title", existingActivity.getTitle());
        assertEquals("Updated Description", existingActivity.getDescription());
        assertEquals("Updated Skills", existingActivity.getRecommendedSkills());
        assertEquals(LocalDate.of(2023, 6, 1), existingActivity.getStartDate());
        assertEquals(LocalDate.of(2023, 6, 30), existingActivity.getEndDate());
    }

    @Test
    void helpSeekerOverViewArchive() {
        HelpSeeker helpSeeker = new HelpSeeker();
        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Activity activity1 = new Activity();
        activity1.setActivityStatus(ActivityStatus.ARCHIVE);
        Activity activity2 = new Activity();
        activity2.setActivityStatus(ActivityStatus.ARCHIVE);
        List<Activity> archivedActivities = Arrays.asList(activity1, activity2);
        Mockito.when(activityRepository.findByActivityStatusAndHelpSeeker(ActivityStatus.ARCHIVE, helpSeeker))
                .thenReturn(archivedActivities);

        List<Activity> result = activityService.helpSeekerOverViewArchive();

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(activityRepository).findByActivityStatusAndHelpSeeker(ActivityStatus.ARCHIVE, helpSeeker);
        assertEquals(2, result.size());
        assertTrue(result.contains(activity1));
        assertTrue(result.contains(activity2));
    }

    @Test
    void volunteerViewArchiveActivities() {
        Volunteer volunteer = new Volunteer();
        Mockito.when(userHelper.getCurrentVolunteer()).thenReturn(volunteer);
        Application application1 = new Application();
        application1.setApplicationStatus(ApplicationStatus.DECLINED);
        Application application2 = new Application();
        application2.setApplicationStatus(ApplicationStatus.DONE);
        List<Application> archiveApplications = Arrays.asList(application1, application2);
        Mockito.when(applicationRepository.findByVolunteerAndApplicationStatusIn(volunteer,
                        List.of(ApplicationStatus.DECLINED, ApplicationStatus.DONE, ApplicationStatus.ABORTED)))
                .thenReturn(archiveApplications);

        List<Application> result = activityService.volunteerViewArchiveActivities();

        Mockito.verify(userHelper).getCurrentVolunteer();
        Mockito.verify(applicationRepository).findByVolunteerAndApplicationStatusIn(volunteer,
                List.of(ApplicationStatus.DECLINED, ApplicationStatus.DONE, ApplicationStatus.ABORTED));
        assertEquals(2, result.size());
        assertTrue(result.contains(application1));
        assertTrue(result.contains(application2));

    }
}