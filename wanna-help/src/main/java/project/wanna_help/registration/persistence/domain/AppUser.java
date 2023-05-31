package project.wanna_help.registration.persistence.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @Email
    @NotBlank(message = "email is mandatory")
    private String email;
    @Column(unique = true)
    @NotBlank(message = "username is mandatory")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$", message = "Username must be alphanumeric")
    private String username;
    @Size(min = 6, message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must have at least one letter and one number")
    private String password;
    @NotNull(message = "role is mandatory")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @NotBlank(message = "full name is mandatory")
    private String fullName;
    private LocalDate dateOfBirth;
    private String address;
    @Column
    private int failedLoginAttempts = 0;


    public AppUser() {
    }

    public AppUser(String email, String username, String password, UserRole role, String fullName, LocalDate dateOfBirth, String address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public AppUser(String email, String username, String password, UserRole role, String fullName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

}
