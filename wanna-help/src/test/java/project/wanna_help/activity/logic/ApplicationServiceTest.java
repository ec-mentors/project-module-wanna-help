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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicationServiceTest {

    @Autowired
    ApplicationService applicationService;
    @MockBean
    ApplicationRepository applicationRepository;
    @MockBean
    ActivityRepository activityRepository;
    @MockBean
    UserHelper userHelper;
    @MockBean
    SecurityFilterChain securityFilterChain;



    @Test
    void acceptThisActivity() {
        HelpSeeker helpSeeker = new HelpSeeker();
        helpSeeker.setId(1L);
        Activity activity = new Activity();
        activity.setId(1L);
        Application application = new Application();
        application.setActivity(activity);
        List<Application> applications = new ArrayList<>();

        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Mockito.when(applicationRepository.findByIdAndApplicationStatusAndActivity_HelpSeeker(1L, ApplicationStatus.PENDING, helpSeeker))
                .thenReturn(Optional.of(application));

        applicationService.acceptThisActivity(1L);

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(applicationRepository).findByIdAndApplicationStatusAndActivity_HelpSeeker(1L,ApplicationStatus.PENDING,helpSeeker);
        Mockito.verify(applicationRepository).save(application);

    }

    @Test
    void declineApplication() {

        HelpSeeker helpSeeker = new HelpSeeker();
        helpSeeker.setId(1L);
        Activity activity = new Activity();
        activity.setId(1L);
        Application application = new Application();
        application.setActivity(activity);
        List<Application> applications = new ArrayList<>();

        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Mockito.when(applicationRepository.findByIdAndApplicationStatusAndActivity_HelpSeeker(1L, ApplicationStatus.PENDING, helpSeeker))
                .thenReturn(Optional.of(application));

        applicationService.declineApplication(1L);

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(applicationRepository).findByIdAndApplicationStatusAndActivity_HelpSeeker(1L, ApplicationStatus.PENDING, helpSeeker);
        Mockito.verify(activityRepository).save(activity);
        Mockito.verify(applicationRepository).save(application);
    }


    @Test
    void markActivityDone() {
        HelpSeeker helpSeeker = new HelpSeeker();
        helpSeeker.setId(1L);
        Activity activity = new Activity();
        activity.setId(1L);
        List<Application> applications = new ArrayList<>();

        Mockito.when(userHelper.getCurrentHelpSeeker()).thenReturn(helpSeeker);
        Mockito.when(activityRepository.findByIdAndHelpSeeker(1L, helpSeeker)).thenReturn(Optional.of(activity));
        Mockito.when(applicationRepository.findByActivity(activity)).thenReturn(applications);


        Application application1 = new Application();
        application1.setApplicationStatus(ApplicationStatus.ENROLLED);
        Application application2 = new Application();
        application2.setApplicationStatus(ApplicationStatus.ENROLLED);
        applications.add(application1);
        applications.add(application2);


        activity.setActivityStatus(ActivityStatus.IN_PROGRESS);

        applicationService.markActivityDone(1L);

        Mockito.verify(userHelper).getCurrentHelpSeeker();
        Mockito.verify(activityRepository).findByIdAndHelpSeeker(1L, helpSeeker);
        Mockito.verify(applicationRepository).findByActivity(activity);
        Mockito.verify(activityRepository).save(activity);


        assertEquals(ApplicationStatus.DONE, application1.getApplicationStatus());
        assertEquals(ApplicationStatus.DONE, application2.getApplicationStatus());
        assertEquals(ActivityStatus.ARCHIVE, activity.getActivityStatus());
    }
}