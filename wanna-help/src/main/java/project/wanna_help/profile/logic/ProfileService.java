package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.appuser.persistence.repository.AppUserRepository;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.communication.dto.HelpSeekerDTO;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityNotFoundException;

@Service
@Validated
public class ProfileService {

    private final VolunteerRepository volunteerRepository;
    private final AppUserRepository appUserRepository;
    private final HelpSeekerRepository helpSeekerRepository;

    private final AppUserConverter appUserConverter;
    private final VolunteerConverter volunteerConverter;

    private final HelpSeekerConverter helpSeekerConverter;

    private final UserHelper userHelper;

    public ProfileService(VolunteerRepository volunteerRepository, AppUserRepository appUserRepository, HelpSeekerRepository helpSeekerRepository, AppUserConverter appUserConverter, VolunteerConverter volunteerConverter, HelpSeekerConverter helpSeekerConverter, UserHelper userHelper) {
        this.volunteerRepository = volunteerRepository;
        this.appUserRepository = appUserRepository;
        this.helpSeekerRepository = helpSeekerRepository;
        this.appUserConverter = appUserConverter;
        this.volunteerConverter = volunteerConverter;
        this.helpSeekerConverter = helpSeekerConverter;
        this.userHelper = userHelper;
    }


    public AppUserDTO getAppUserData() {
        return appUserConverter.converterUserToDTO(userHelper.getCurrentUser());
    }

    public AppUserDTO updateAppUserData(AppUserDTO appUserDTO) {
        AppUser appUser = userHelper.getCurrentUser();
        appUser.setEmail(appUserDTO.getEmail());
        appUser.setFullName(appUserDTO.getFullName());
        appUser.setDateOfBirth(appUserDTO.getDateOfBirth());
        appUser.setAddress(appUserDTO.getAddress());
        return appUserConverter.converterUserToDTO(appUserRepository.save(appUser));

    }

    public String getVolunteerSkills() {
        Volunteer volunteer = userHelper.getCurrentVolunteer();
        return volunteer.getMySkills();
    }

    public String updateVolunteerSkills(String skills) {
        Volunteer volunteer = userHelper.getCurrentVolunteer();
        volunteer.setMySkills(skills);
        return volunteerRepository.save(volunteer).getMySkills();
    }


    public VolunteerDTO getVolunteerProfileSeenByOthers(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Volunteer doesn't exist."));
        return volunteerConverter.convertVolunteerToDTO(volunteer);
    }

    public HelpSeekerDTO getHelpSeekerProfileSeenByOthers(Long id) {
        HelpSeeker helpSeeker = helpSeekerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HelpSeeker doesn't exist."));
        return helpSeekerConverter.convertHelpSeekerToDTO(helpSeeker);
    }


}
