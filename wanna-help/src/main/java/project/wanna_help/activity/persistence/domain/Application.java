package project.wanna_help.activity.persistence.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String comment;
    @NotNull
    private LocalDateTime timeStamp;
    @ManyToOne
    private Activity activity;
    @ManyToOne
//    @JsonBackReference
    private Volunteer volunteer;

    public Application() {
    }

    public Application(Long id, ApplicationStatus applicationStatus, LocalDateTime timeStamp, Activity activity, Volunteer volunteer) {
        this.id = id;
        this.applicationStatus = applicationStatus;
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
