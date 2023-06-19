package project.wanna_help.notifications;

import java.time.LocalDateTime;

public class NotificationDTO {

    private LocalDateTime notification_date;
    private String notification_Message;

    public NotificationDTO() {
    }

    public NotificationDTO(LocalDateTime notification_date, String notification_Message) {
        this.notification_date = notification_date;
        this.notification_Message = notification_Message;
    }

    public LocalDateTime getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(LocalDateTime notification_date) {
        this.notification_date = notification_date;
    }

    public String getNotification_Message() {
        return notification_Message;
    }

    public void setNotification_Message(String notification_Message) {
        this.notification_Message = notification_Message;
    }
}
