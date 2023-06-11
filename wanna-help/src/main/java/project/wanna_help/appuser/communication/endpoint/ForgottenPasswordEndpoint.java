package project.wanna_help.appuser.communication.endpoint;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import project.wanna_help.appuser.logic.ForgottenPasswordService;
import project.wanna_help.appuser.communication.dto.Passwords;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class ForgottenPasswordEndpoint {

    private final ForgottenPasswordService forgottenPasswordService;

    public ForgottenPasswordEndpoint(ForgottenPasswordService forgottenPasswordService) {
        this.forgottenPasswordService = forgottenPasswordService;
    }


    @PostMapping("/password-reset")
    ResponseEntity<String> sendPasswordResetEmail(@RequestBody String nameOrEmail) {
        forgottenPasswordService.generatePasswordResetLink(nameOrEmail);
        return ResponseEntity.ok("Password reset link has been successfully sent to your email"); // HTTP 200 OK
    }

    @PostMapping("/password-reset/{token}")
    ResponseEntity<String> resetPassword(@PathVariable String token, @Valid @RequestBody Passwords passwords) {
        forgottenPasswordService.resetPassword(token, passwords.getPassword1(), passwords.getPassword2());
        return ResponseEntity.ok("Password has been successfully reset");
    }

}
