package project.wanna_help.profile.persistence.domain;

import project.wanna_help.Activity.Activity;
import project.wanna_help.persistence.domain.AppUser;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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
    private ExperienceLevel experienceLevel = ExperienceLevel.BRONZE;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Activity> applications;

    public Volunteer() {
    }

    public Volunteer(AppUser appUser, String mySkills, ExperienceLevel experienceLevel, Set<Activity> applications) {
        this.appUser = appUser;
        this.mySkills = mySkills;
        this.experienceLevel = experienceLevel;
        this.applications = applications;
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

    public Set<Activity> getApplications() {
        return applications;
    }

    public void setApplications(Set<Activity> applications) {
        this.applications = applications;
    }
}

