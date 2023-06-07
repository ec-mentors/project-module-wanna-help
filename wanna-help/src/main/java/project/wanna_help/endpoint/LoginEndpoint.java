package project.wanna_help.endpoint;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import project.wanna_help.logic.LoginService;
//import project.wanna_help.persistence.domain.AppUser;
//import project.wanna_help.persistence.dto.LoginDto;
//import project.wanna_help.persistence.repository.AppUserRepository;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/users/login")
//public class LoginEndpoint {
//    private final LoginService loginService;
//
//
//    public LoginEndpoint(LoginService loginService, AppUserRepository appUserRepository) {
//        this.loginService = loginService;
//    }
//
//    @PostMapping
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
//        Optional<AppUser> appUser = loginService.login(loginDto.getUsernameOrEmail(), loginDto.getPassword());
//        if (appUser.isPresent()) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email or password");
//        }
//    }
//}


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/login")
@Secured({"ROLE_VOLUNTEER", "ROLE_INDIVIDUAL", "ROLE_ORGANIZATION"})
public class LoginEndpoint {


    @PostMapping
    ResponseEntity<String> login() {
        return new ResponseEntity<>("Login Success", HttpStatus.OK);
    }

}





