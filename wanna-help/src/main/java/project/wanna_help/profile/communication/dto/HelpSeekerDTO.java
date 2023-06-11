package project.wanna_help.profile.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import project.wanna_help.appuser.persistence.domain.UserRole;
import project.wanna_help.profile.persistence.domain.Rating;

import java.time.LocalDate;
import java.util.List;

public class HelpSeekerDTO {

    private String email;
    private String username;
    private UserRole userRole;
    private String fullName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private String address;

    private List<Rating> ratings;

    public HelpSeekerDTO() {
    }

    public HelpSeekerDTO(String email, String username, UserRole userRole, String fullName, LocalDate dateOfBirth, String address, List<Rating> ratings) {
        this.email = email;
        this.username = username;
        this.userRole = userRole;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.ratings = ratings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
