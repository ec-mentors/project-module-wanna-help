package project.wanna_help.profile.communication.dto;

import project.wanna_help.persistence.domain.UserRole;

import java.time.LocalDate;

public class AppUserDTO {

    private String email;
    private String username;
    private UserRole role;
    private String fullName;
    private LocalDate dateOfBirth;
    private String address;

    public AppUserDTO() {
    }

    public AppUserDTO(String email, String username, UserRole role, String fullName, LocalDate dateOfBirth, String address) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
}
