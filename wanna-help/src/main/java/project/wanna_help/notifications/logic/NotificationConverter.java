package project.wanna_help.notifications.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.notifications.persistence.domain.Notification;
import project.wanna_help.notifications.communication.dto.NotificationDTO;

@Service
public class NotificationConverter {

    public NotificationDTO convertNotificationToNotificationDTO(Notification notification) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotification_Message(notification.getNotificationMessage());
        notificationDTO.setNotification_date(notification.getNotificationDate());
        return notificationDTO;
    }
}
