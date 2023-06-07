package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.persistence.domain.Volunteer;
@Service
public class VolunteerConverter {


    public VolunteerDTO convertVolunteerToDTO(Volunteer volunteer) {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUsername(volunteer.getAppUser().getUsername());
        volunteerDTO.setDateOfBirth(volunteer.getAppUser().getDateOfBirth());
        volunteerDTO.setAddress(volunteer.getAppUser().getAddress());
        volunteerDTO.setMySkills(volunteer.getMySkills());
        volunteerDTO.setExperienceLevel(volunteer.getExperienceLevel());
        return volunteerDTO;
    }

    public Volunteer convertDTOtoVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer volunteer = new Volunteer();
        volunteer.getAppUser().setUsername(volunteerDTO.getUsername());
        volunteer.getAppUser().setDateOfBirth(volunteerDTO.getDateOfBirth());
        volunteer.getAppUser().setAddress(volunteerDTO.getAddress());
        volunteer.setMySkills(volunteerDTO.getMySkills());
        return volunteer;

    }


}
