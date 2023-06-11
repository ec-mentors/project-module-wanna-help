package project.wanna_help.activity.persistence.domain;

import project.wanna_help.profile.persistence.domain.Volunteer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Application {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    @NotBlank
    private String comment;
    @NotNull
    private LocalDateTime timeStamp;
    @ManyToOne
    private Activity activity;
    @ManyToOne
    private Volunteer volunteer;

    public Application() {
    }

    public Application(ApplicationStatus applicationStatus, String comment, LocalDateTime timeStamp, Activity activity, Volunteer volunteer) {
        this.applicationStatus = applicationStatus;
        this.comment = comment;
        this.timeStamp = timeStamp;
        this.activity = activity;
        this.volunteer = volunteer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
}
