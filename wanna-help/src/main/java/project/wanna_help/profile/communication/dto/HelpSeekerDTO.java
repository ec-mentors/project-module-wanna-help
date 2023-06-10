package project.wanna_help.profile.communication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import project.wanna_help.persistence.domain.UserRole;
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

    private double averageRating;

    private int totalRatings;


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

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public HelpSeekerDTO() {
    }

    public HelpSeekerDTO(String email, String username, UserRole userRole, String fullName, LocalDate dateOfBirth, String address, double averageRating, int totalRatings) {
        this.email = email;
        this.username = username;
        this.userRole = userRole;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
    }
}
