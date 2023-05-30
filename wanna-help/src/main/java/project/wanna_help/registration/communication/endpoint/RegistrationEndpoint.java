package project.wanna_help.registration.communication.endpoint;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.wanna_help.registration.logic.RegistrationService;
import project.wanna_help.registration.persistence.domain.AppUser;

@RestController
@RequestMapping("/users")
@Validated
public class RegistrationEndpoint {

   private final RegistrationService registrationService;

    public RegistrationEndpoint(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    AppUser register(@RequestBody AppUser appUser){
       return registrationService.register(appUser);
    }

}
