package project.wanna_help.notifications;


import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime notificationDate;
    @NotNull
    private String notificationMessage;


    @ManyToOne
    private Volunteer volunteer;

    @ManyToOne
    private HelpSeeker helpSeeker;

    public Notification() {
    }

    public Notification(Long id, LocalDateTime notificationDate, String notificationMessage, Volunteer volunteer, HelpSeeker helpSeeker) {
        this.id = id;
        this.notificationDate = notificationDate;
        this.notificationMessage = notificationMessage;
        this.volunteer = volunteer;
        this.helpSeeker = helpSeeker;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public HelpSeeker getHelpSeeker() {
        return helpSeeker;
    }

    public void setHelpSeeker(HelpSeeker helpSeeker) {
        this.helpSeeker = helpSeeker;
    }
}
