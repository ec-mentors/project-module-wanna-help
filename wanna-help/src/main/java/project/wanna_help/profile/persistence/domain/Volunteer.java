package project.wanna_help.profile.persistence.domain;

import project.wanna_help.persistence.domain.AppUser;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Entity
public class Volunteer {


    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AppUser appUser;
    @Pattern(regexp = "^[a-zA-Z]+(?:;[a-zA-Z]+)*$", message = "Invalid skills format. Skills must be delimited by ';' and contain only letters.")
    private String mySkills;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel = ExperienceLevel.BRONZE;

    public Volunteer() {
    }

    public Volunteer(AppUser appUser, String mySkills, ExperienceLevel experienceLevel) {
        this.appUser = appUser;
        this.mySkills = mySkills;
        this.experienceLevel = experienceLevel;
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
}

