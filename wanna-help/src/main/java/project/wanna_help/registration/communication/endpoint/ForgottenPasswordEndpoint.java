package project.wanna_help.registration.communication.endpoint;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import project.wanna_help.registration.logic.ForgottenPasswordService;
import project.wanna_help.registration.persistence.dto.Passwords;


@RestController
@RequestMapping("/users/")
public class ForgottenPasswordEndpoint {

    private final ForgottenPasswordService forgottenPasswordService;

    public ForgottenPasswordEndpoint(ForgottenPasswordService forgottenPasswordService) {
        this.forgottenPasswordService = forgottenPasswordService;
    }


    @PostMapping("/passwordresetlink")
    ResponseEntity sendPasswordRestEmail(@RequestBody String nameOrEmail) {
        forgottenPasswordService.generatePasswordResetLink(nameOrEmail);
        return ResponseEntity.ok().build(); // HTTP 200 OK
    }

    @PostMapping("/password-reset/{token}")
    ResponseEntity resetPassword(@PathVariable String token, @RequestBody Passwords passwords) {
        forgottenPasswordService.resetPassword(token, passwords.getPassword1(), passwords.getPassword2());
        return ResponseEntity.ok().build();
    }

}
