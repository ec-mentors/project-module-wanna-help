package project.wanna_help.profile.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.appuser.persistence.domain.AppUser;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.beans.Visibility;
import java.util.Set;


@Entity
public class Volunteer {


    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    private AppUser appUser;
    @Pattern(regexp = "^[A-Za-z ;]+$", message = "Invalid skills format. Skills must be delimited by ';' and contain only letters.")
    private String mySkills;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel = ExperienceLevel.ROOKIE;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    @JsonIgnore
    private Set<Application> applications;

    @Enumerated(EnumType.STRING)
    private VisibilityStatus visibilityStatus = VisibilityStatus.VISIBLE;

    private long completedActivitiesCount;

    public Volunteer() {
    }

    public Volunteer(AppUser appUser, String mySkills, ExperienceLevel experienceLevel, Set<Application> applications, VisibilityStatus visibilityStatus) {
        this.appUser = appUser;
        this.mySkills = mySkills;
        this.experienceLevel = experienceLevel;
        this.applications = applications;
        this.visibilityStatus = visibilityStatus;
    }

    public VisibilityStatus getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(VisibilityStatus visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
    }

    public long getCompletedActivitiesCount() {
        return completedActivitiesCount;
    }

    public void setCompletedActivitiesCount(long completedActivitiesCount) {
        this.completedActivitiesCount = completedActivitiesCount;
    }

    public Volunteer(AppUser appUser) {
        this.appUser = appUser;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }


    public String getMySkills() {
        return mySkills;
    }

    public void setMySkills(String mySkills) {
        this.mySkills = mySkills;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}

