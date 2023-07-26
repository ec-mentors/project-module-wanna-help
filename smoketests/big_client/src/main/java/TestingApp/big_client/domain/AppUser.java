package TestingApp.big_client.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AppUser {

    private String id;

    private String email;

    private String username;

    private String password;

    private String role;

    private String fullName;


    public AppUser(String id, String email, String username, String password, String role, String fullName) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    public AppUser(String email, String username, String password, String role, String fullName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    public AppUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
