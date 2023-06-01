package project.wanna_help.registration.communication.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.wanna_help.registration.logic.LoginService;
import project.wanna_help.registration.persistence.domain.AppUser;
import project.wanna_help.registration.persistence.repository.AppUserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginEndpoint {
    private final LoginService loginService;


    public LoginEndpoint(LoginService loginService, AppUserRepository appUserRepository) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Optional<AppUser> appUser = loginService.login(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        if (appUser.isPresent()) {
            String message = "Login successful";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email or password");
        }
    }
}





