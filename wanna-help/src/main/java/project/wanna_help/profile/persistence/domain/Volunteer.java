package project.wanna_help.profile.persistence.domain;

import project.wanna_help.persistence.domain.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Volunteer {


    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AppUser appUser;
    @ElementCollection
    private List<String> mySkills = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel = ExperienceLevel.BRONZE;


    public Volunteer() {
    }

    public Volunteer(AppUser appUser) {
        this.appUser = appUser;
    }

    public Volunteer(AppUser appUser, List<String> mySkills, ExperienceLevel experienceLevel) {
        this.appUser = appUser;
        this.mySkills = mySkills;
        this.experienceLevel = experienceLevel;
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

    public List<String> getMySkills() {
        return mySkills;
    }

    public void setMySkills(List<String> mySkills) {
        this.mySkills = mySkills;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}

