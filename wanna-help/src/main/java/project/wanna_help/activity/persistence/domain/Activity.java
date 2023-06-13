package project.wanna_help.activity.persistence.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import project.wanna_help.profile.persistence.domain.HelpSeeker;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

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
    private ActivityStatus activityStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private HelpSeeker helpSeeker;

    @OneToMany(
            mappedBy = "activity",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Application> applications;


    public Activity() {
    }

    public Activity(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity(String title, String description, String recommendedSkills, LocalDate startDate, LocalDate endDate, ActivityStatus activityStatus, HelpSeeker helpSeeker, List<Application> applications) {
        this.title = title;
        this.description = description;
        this.recommendedSkills = recommendedSkills;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityStatus = activityStatus;
        this.helpSeeker = helpSeeker;
        this.applications = applications;
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


    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public HelpSeeker getHelpSeeker() {
        return helpSeeker;
    }

    public void setHelpSeeker(HelpSeeker helpSeeker) {
        this.helpSeeker = helpSeeker;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
