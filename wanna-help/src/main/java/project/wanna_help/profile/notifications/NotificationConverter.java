package project.wanna_help.profile.notifications;

import org.springframework.stereotype.Service;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.persistence.domain.Rating;

@Service
public class NotificationConverter {

    public NotificationDTO convertNotificationToNotificationDTO(Notification notification) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotification_Message(notification.getNotificationMessage());
        notificationDTO.setNotification_date(notification.getNotificationDate());
        return notificationDTO;
    }
}
