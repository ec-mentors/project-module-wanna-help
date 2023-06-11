package project.wanna_help.Activity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "title is mandatory")
    @Size(max = 40, message = "title must have a maximum of 40 characters")
    private String title;

    @NotBlank(message = "description is mandatory")
    private String description;

    @Pattern(regexp = "^[A-Za-z0-9 ;]+$", message = "skills should be divided with ; ")
    private String recommendedSkills;

    @NotNull(message = "start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "end date is mandatory")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean pending;
    private boolean done;
    private boolean aborted;

    private String comment;

    private LocalDateTime timeStamp;

    public Activity() {
    }

    public Activity(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity(String title, String description, String recommendedSkills, LocalDate startDate, LocalDate endDate, Status status) {
        this.title = title;
        this.description = description;
        this.recommendedSkills = recommendedSkills;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Activity(String title, String description, String recommendedSkills, LocalDate startDate, LocalDate endDate, Status status, boolean pending, boolean done, boolean aborted) {
        this.title = title;
        this.description = description;
        this.recommendedSkills = recommendedSkills;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.pending = pending;
        this.done = done;
        this.aborted = aborted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendedSkills() {
        return recommendedSkills;
    }

    public void setRecommendedSkills(String recommendedSkills) {
        this.recommendedSkills = recommendedSkills;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isAborted() {
        return aborted;
    }

    public void setAborted(boolean aborted) {
        this.aborted = aborted;
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
}
