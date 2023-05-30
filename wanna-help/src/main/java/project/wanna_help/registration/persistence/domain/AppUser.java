package project.wanna_help.registration.persistence.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    //TODO alphanumeric validation
    private String username;
    @Size(min = 6, message = "password is mandatory")
    private String password;
    @NotNull(message = "role is mandatory")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @NotBlank(message = "full name is mandatory")
    private String fullName;
    private Date dateOfBirth;
    private String address;
    @Column
    private int failedLoginAttempts = 0;


    public AppUser() {
    }

    public AppUser(String email, String username, String password, UserRole role, String fullName, Date dateOfBirth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
