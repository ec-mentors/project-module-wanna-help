package project.wanna_help.notifications;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.ActivityStatus;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.activity.persistence.domain.ApplicationStatus;
import project.wanna_help.activity.persistence.repository.ActivityRepository;
import project.wanna_help.activity.persistence.repository.ApplicationRepository;
import project.wanna_help.appuser.logic.EmailRedirector;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final EmailRedirector redirector;
    private final NotificationRepository notificationRepository;
    private final VolunteerRepository volunteerRepository;
    private final ActivityRepository activityRepository;
    private final NotificationConverter notificationConverter;
    private final ApplicationRepository applicationRepository;
    private final UserHelper userHelper;
    private final String emailContent;

    public NotificationService(EmailRedirector redirector, NotificationRepository notificationRepository, VolunteerRepository volunteerRepository, ActivityRepository activityRepository, NotificationConverter notificationConverter, ApplicationRepository applicationRepository, UserHelper userHelper,
                               @Value("${email_content}") String emailContent) {
        this.redirector = redirector;
        this.notificationRepository = notificationRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityRepository = activityRepository;
        this.notificationConverter = notificationConverter;
        this.applicationRepository = applicationRepository;
        this.userHelper = userHelper;
        this.emailContent = emailContent;
    }

    // Help-seeker send an invitation to a specific activity
    public String sendNotification(Long volunteerId, Long activityId, String notificationMessage) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Volunteer not found with id: " + volunteerId));
        HelpSeeker helpSeeker = userHelper.getCurrentHelpSeeker();
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));
        Notification notification = new Notification();
        LocalDateTime currentDateTime = LocalDateTime.now();
        notification.setNotificationDate(currentDateTime);
        notification.setNotificationMessage(notificationMessage);
        notification.setHelpSeeker(helpSeeker);
        notification.setVolunteer(volunteer);
        notificationRepository.save(notification);
        String acceptLink = acceptHelpSeekerInvitationLink(volunteerId, activityId);
        String declineLink = declineHelpSeekerInvitationLink(volunteerId, activityId);

        String subject = "New Notification";
        String content = emailContent.replace("{{ helpSeekerName }}", helpSeeker.getAppUser().getUsername())
                .replace("{{ activityDetails }}", activity.toString())
                .replace("{{ acceptLink }}", acceptLink)
                .replace("{{ declineLink }}", declineLink);

        redirector.redirectEMail(volunteer.getAppUser().getEmail(), subject, content);

        return "The notification has been successfully sent";
    }

    //    volunteer can see all notifications sent to him
    public List<NotificationDTO> getAllNotification() {
        Volunteer volunteer = userHelper.getCurrentVolunteer();
        List<Notification> notifications = notificationRepository.findAllByVolunteer(volunteer);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        notifications.sort(Comparator.comparing(Notification::getNotificationDate).reversed());

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = notificationConverter.convertNotificationToNotificationDTO(notification);
            notificationDTOS.add(notificationDTO);

        }
        return notificationDTOS;
    }

    //  accept help-seeker invitation link
    public String acceptHelpSeekerInvitationLink(Long volunteerId, Long activityId) {
        return "http://localhost:9100/profile/organization-individual/" + volunteerId + "/accept_invitation?activityId=" + activityId;
    }

    //  reject help-seeker invitation link
    public String declineHelpSeekerInvitationLink(Long volunteerId, Long activityId) {
        return "http://localhost:9100/profile/organization-individual/" + volunteerId + "/decline_invitation?activityId=" + activityId;
    }

    // Change the application status of the volunteer to be enrolled after accepting the invitation sent to im by email
    public void acceptInvitationStatus(Long volunteerId, Long activityId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Volunteer not found with id: " + volunteerId));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));

        Optional<Application> oApplication = applicationRepository.findByVolunteerAndActivity(volunteer, activity);
        LocalDateTime localDateTime = LocalDateTime.now();
        ApplicationStatus applicationStatus = ApplicationStatus.ENROLLED;
        activity.setActivityStatus(ActivityStatus.IN_PROGRESS);

        if (oApplication.isEmpty()) {
            Application application = new Application();
            application.setApplicationStatus(applicationStatus);
            application.setTimeStamp(localDateTime);
            application.setActivity(activity);
            application.setVolunteer(volunteer);
            applicationRepository.save(application);
        } else {
            Application application = oApplication.get();
            application.setApplicationStatus(applicationStatus);
            application.setTimeStamp(localDateTime);
            applicationRepository.save(application);
        }
    }

    // Change the application status of the volunteer to be aborted after declining the invitation sent to im by email
     public void declineInvitationStatus(Long volunteerId, Long activityId) {
         Volunteer volunteer = volunteerRepository.findById(volunteerId)
                 .orElseThrow(() -> new EntityNotFoundException("Volunteer not found with id: " + volunteerId));
         Activity activity = activityRepository.findById(activityId)
                 .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));

         Optional<Application> oApplication = applicationRepository.findByVolunteerAndActivity(volunteer, activity);
         LocalDateTime localDateTime = LocalDateTime.now();
         ApplicationStatus applicationStatus = ApplicationStatus.ABORTED;

         if (oApplication.isEmpty()) {
             Application application = new Application();
             application.setApplicationStatus(applicationStatus);
             application.setTimeStamp(localDateTime);
             application.setActivity(activity);
             application.setVolunteer(volunteer);
             applicationRepository.save(application);
         } else {
             Application application = oApplication.get();
             application.setApplicationStatus(applicationStatus);
             application.setTimeStamp(localDateTime);
             applicationRepository.save(application);
         }
     }

}
