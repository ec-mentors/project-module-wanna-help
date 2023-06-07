package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.profile.communication.dto.AppUserDTO;

@Service
public class AppUserConverter {


    public AppUserDTO converterUserToDTO(AppUser appUser) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail(appUser.getEmail());
        appUserDTO.setUsername(appUser.getUsername());
        appUserDTO.setRole(appUser.getRole());
        appUserDTO.setFullName(appUser.getFullName());
        appUserDTO.setDateOfBirth(appUser.getDateOfBirth());
        appUserDTO.setAddress(appUser.getAddress());
        return appUserDTO;

    }



}
