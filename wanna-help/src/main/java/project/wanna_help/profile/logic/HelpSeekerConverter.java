package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.profile.communication.dto.HelpSeekerDTO;
import project.wanna_help.profile.persistence.domain.HelpSeeker;

@Service
public class HelpSeekerConverter {

    public HelpSeekerDTO convertHelpSeekerToDTO(HelpSeeker helpSeeker){
        HelpSeekerDTO helpSeekerDTO = new HelpSeekerDTO();
        helpSeekerDTO.setEmail(helpSeeker.getAppUser().getEmail());
        helpSeekerDTO.setUsername(helpSeeker.getAppUser().getFullName());
        helpSeekerDTO.setUserRole(helpSeeker.getAppUser().getRole());
        helpSeekerDTO.setFullName(helpSeeker.getAppUser().getFullName());
        helpSeekerDTO.setDateOfBirth(helpSeeker.getAppUser().getDateOfBirth());
        helpSeekerDTO.setAddress(helpSeeker.getAppUser().getAddress());
        helpSeekerDTO.setRatings(helpSeeker.getRatings());
        helpSeekerDTO.setApplications(helpSeeker.getApplications());
        return helpSeekerDTO;
    }




}
