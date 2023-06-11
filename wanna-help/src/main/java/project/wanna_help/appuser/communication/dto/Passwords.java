package project.wanna_help.appuser.communication.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Passwords {
    @Size(min = 6, message = "password must has at least 6 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must have at least one letter and one number")
    String password1;
    String password2;

    public Passwords() {
    }

    public Passwords(String password1, String password2) {
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
