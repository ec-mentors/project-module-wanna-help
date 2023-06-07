package project.wanna_help.profile;

import org.springframework.stereotype.Service;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.repository.AppUserRepository;

import java.util.Optional;

@Service
public class ProfileService {
    private final AppUserRepository repository;
    private final VolunteerRepository volunteerRepository;

    public ProfileService(AppUserRepository repository, VolunteerRepository volunteerRepository) {
        this.repository = repository;
        this.volunteerRepository = volunteerRepository;
    }

    public Optional<AppUser> getAccountById(Long id) {
        return repository.findById(id);
    }
    public Optional<Volunteer> getProfileById(Long id) {
        return volunteerRepository.findById(id);
    }

    public AppUser updateProfile(AppUser existingUser, UserProfileDTO userProfileDTO) {

        existingUser.setEmail(userProfileDTO.getEmail());
        existingUser.setFullName(userProfileDTO.getFullName());
        existingUser.setDateOfBirth(userProfileDTO.getDateOfBirth());
        existingUser.setAddress(userProfileDTO.getAddress());

        return repository.save(existingUser);
    }

    public String saveSkills(Long id, String skills) {
        Optional<AppUser> oVolunteer = repository.findById(id);
        if (oVolunteer.isPresent()) {
            Volunteer volunteer = new Volunteer();
            volunteer.setSkills(skills);
            volunteer.setAppUser(oVolunteer.get());
            volunteerRepository.save(volunteer);
        }
        return null;
    }

}
