package project.wanna_help.appuser.communication.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.wanna_help.appuser.communication.dto.RegisterDTO;
import project.wanna_help.appuser.logic.RegistrationConverter;
import project.wanna_help.appuser.logic.RegistrationService;
import project.wanna_help.appuser.persistence.domain.AppUser;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class RegistrationEndpoint {
    private final RegistrationService registrationService;
    private final RegistrationConverter registrationConverter;

    public RegistrationEndpoint(RegistrationService registrationService, RegistrationConverter registrationConverter) {
        this.registrationService = registrationService;
        this.registrationConverter = registrationConverter;
    }

    @PostMapping
    RegisterDTO register(@Valid @RequestBody AppUser appUser) {
        return registrationConverter.appUserToRegisterDto(registrationService.register(appUser));
    }

}
