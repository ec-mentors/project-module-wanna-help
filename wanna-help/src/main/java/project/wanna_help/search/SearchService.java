package project.wanna_help.search;

import org.springframework.stereotype.Service;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.logic.VolunteerConverter;
import project.wanna_help.profile.persistence.domain.ExperienceLevel;
import project.wanna_help.profile.persistence.domain.VisibilityStatus;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerConverter volunteerConverter;

    public SearchService(VolunteerRepository volunteerRepository, VolunteerConverter volunteerConverter) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerConverter = volunteerConverter;
    }


    public List<VolunteerDTO> showAvailableVolunteers() {
        var availableVolunteers = volunteerRepository.findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus.VISIBLE);
        List<VolunteerDTO> volunteerDTOS = new ArrayList<>();
        if (!availableVolunteers.isEmpty()) {
            for (Volunteer volunteer : availableVolunteers) {
                volunteerDTOS.add(volunteerConverter.convertVolunteerToDTO(volunteer));
            }
            return volunteerDTOS;
        }
        throw new EntityNotFoundException("There are no available volunteers at the moment");
    }

    public List<VolunteerDTO> showSearchedVolunteers(String searchedWord) {
        if (searchedWord.length() > 2) {
            var availableVolunteers = volunteerRepository.findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus.VISIBLE);
            List<VolunteerDTO> volunteerDTOS = new ArrayList<>();
            if (!availableVolunteers.isEmpty()) {
                for (Volunteer volunteer : availableVolunteers) {
                    if (volunteer.getMySkills() != null) {
                        volunteerDTOS.add(volunteerConverter.convertVolunteerToDTO(volunteer));
                    }
                }
                var skills = volunteerDTOS.stream().filter(volunteerDTO -> volunteerDTO.getMySkills().contains(searchedWord)).collect(Collectors.toList());
                return skills;
            }
            throw new EntityNotFoundException("There are no available volunteers at the moment");
        } else {
            throw new IllegalArgumentException("Searched word is too short, minimum length 3 characters.");
        }
    }
    public List<Volunteer> searchByExperienceLevel(ExperienceLevel experienceLevel) {
        List<Volunteer> volunteers = volunteerRepository.findAllByExperienceLevel(experienceLevel);
        if (!volunteers.isEmpty()) {
           volunteers.forEach(volunteerConverter::convertVolunteerToDTO);
           return volunteers;
        }
        throw new EntityNotFoundException("There are no available volunteers at the moment");
    }
}
