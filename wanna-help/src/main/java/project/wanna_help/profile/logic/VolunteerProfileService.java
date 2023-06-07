package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.logic.UserHelper;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.repository.AppUserRepository;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Validated
public class VolunteerProfileService {

    private final VolunteerRepository volunteerRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserConverter appUserConverter;
    private final VolunteerConverter volunteerConverter;

    public VolunteerProfileService(VolunteerRepository volunteerRepository, AppUserRepository appUserRepository, AppUserConverter appUserConverter, VolunteerConverter volunteerConverter) {
        this.volunteerRepository = volunteerRepository;
        this.appUserRepository = appUserRepository;
        this.appUserConverter = appUserConverter;
        this.volunteerConverter = volunteerConverter;
    }


    public AppUserDTO getAppUserData() {
        return appUserConverter.converterUserToDTO(UserHelper.getCurrentUser());
    }

    public AppUserDTO updateAppUserData(AppUserDTO appUserDTO) {
        AppUser appUser = UserHelper.getCurrentUser();
        appUser.setEmail(appUserDTO.getEmail());
        appUser.setFullName(appUserDTO.getFullName());
        appUser.setDateOfBirth(appUserDTO.getDateOfBirth());
        appUser.setAddress(appUserDTO.getAddress());
        return appUserConverter.converterUserToDTO(appUserRepository.save(appUser));

    }

    public String getVolunteerSkills() {
        Volunteer volunteer = getCurrentVolunteer();
        return volunteer.getMySkills();
    }

    public String updateVolunteerSkills(String skills) {
        Volunteer volunteer = getCurrentVolunteer();
        volunteer.setMySkills(skills);
        return volunteerRepository.save(volunteer).getMySkills();
    }


    public VolunteerDTO getVolunteerProfileSeenByOthers(Long id){
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Volunteer doesn't exist."));
        return volunteerConverter.convertVolunteerToDTO(volunteer);
    }

    public Volunteer getCurrentVolunteer() {
        AppUser currentUser = UserHelper.getCurrentUser();
        Optional<Volunteer> oVolunteer = volunteerRepository.findByAppUser(currentUser);
        if (oVolunteer.isPresent()) {
            return oVolunteer.get();
        }
        throw new EntityNotFoundException();
    }

}
