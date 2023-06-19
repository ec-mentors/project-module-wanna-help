package project.wanna_help.notifications;

import org.springframework.stereotype.Service;

@Service
public class NotificationConverter {

    public NotificationDTO convertNotificationToNotificationDTO(Notification notification) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotification_Message(notification.getNotificationMessage());
        notificationDTO.setNotification_date(notification.getNotificationDate());
        return notificationDTO;
    }
}
