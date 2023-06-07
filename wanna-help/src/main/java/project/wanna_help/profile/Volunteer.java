package project.wanna_help.profile;

import project.wanna_help.persistence.domain.AppUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

@Entity
public class Volunteer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private AppUser appUser;

    @Pattern(regexp = "^[a-zA-Z]+(?:;[a-zA-Z]+)*$", message = "Invalid skills format. Skills must be delimited by ';' and contain only letters.")
    private String skills;

    public Volunteer(AppUser appUser, String skills) {
        this.appUser = appUser;
        this.skills = skills;
    }

    public Volunteer(AppUser appUser) {
        this.appUser = appUser;
    }

    public Volunteer() {
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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
