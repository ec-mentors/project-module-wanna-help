package project.wanna_help.appuser.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.appuser.communication.dto.RegisterDTO;
import project.wanna_help.appuser.persistence.domain.AppUser;

@Service
public class RegistrationConverter {

    public RegisterDTO appUserToRegisterDto(AppUser appUser){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail(appUser.getEmail());
        registerDTO.setUsername(appUser.getUsername());
        registerDTO.setRole(appUser.getRole());
        registerDTO.setFullName(appUser.getFullName());
        registerDTO.setDateOfBirth(appUser.getDateOfBirth());
        registerDTO.setAddress(appUser.getAddress());
        return registerDTO;
    }


}
