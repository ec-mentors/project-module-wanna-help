package project.wanna_help.profile.notifications;

import org.springframework.stereotype.Service;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.repository.ActivityRepository;
import project.wanna_help.appuser.logic.EmailRedirector;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Rating;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NotificationService {
    private final EmailRedirector redirector;
    private final NotificationRepository notificationRepository;
    private final VolunteerRepository volunteerRepository;
    private final ActivityRepository activityRepository;
    private final NotificationConverter notificationConverter;
    private final UserHelper userHelper;

    public NotificationService(EmailRedirector redirector, NotificationRepository notificationRepository, VolunteerRepository volunteerRepository, ActivityRepository activityRepository, NotificationConverter notificationConverter, UserHelper userHelper) {
        this.redirector = redirector;
        this.notificationRepository = notificationRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityRepository = activityRepository;
        this.notificationConverter = notificationConverter;
        this.userHelper = userHelper;
    }

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
        String subject = "New Notification";
        String content = "Dear Volunteer," + "\n\n You have an invitation from " + helpSeeker.getAppUser().getUsername() + " to participate in the new activity: \n\n" + activity;
        redirector.redirectEMail(volunteer.getAppUser().getEmail(), subject, content);
        return "The notification has been successfully sent";
    }

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
}
