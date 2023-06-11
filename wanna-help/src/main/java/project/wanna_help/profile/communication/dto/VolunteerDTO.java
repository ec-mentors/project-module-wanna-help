package project.wanna_help.profile.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import project.wanna_help.Activity.Activity;
import project.wanna_help.profile.persistence.domain.ExperienceLevel;

import java.time.LocalDate;
import java.util.Set;

public class VolunteerDTO {

    private String username;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private String address;
    private String mySkills;

    private ExperienceLevel experienceLevel;

    private Set<Activity> applications;

    public VolunteerDTO() {
    }

    public VolunteerDTO(String username, LocalDate dateOfBirth, String address, String mySkills, ExperienceLevel experienceLevel, Set<Activity> applications) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mySkills = mySkills;
        this.experienceLevel = experienceLevel;
        this.applications = applications;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
