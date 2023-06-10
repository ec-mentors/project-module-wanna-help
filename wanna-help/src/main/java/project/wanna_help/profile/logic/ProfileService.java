package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.wanna_help.logic.UserHelper;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.repository.AppUserRepository;
import project.wanna_help.profile.communication.dto.AppUserDTO;
import project.wanna_help.profile.communication.dto.HelpSeekerDTO;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Rating;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.RatingRepository;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ProfileService {

    private final VolunteerRepository volunteerRepository;
    private final AppUserRepository appUserRepository;
    private final HelpSeekerRepository helpSeekerRepository;
    private final RatingRepository ratingRepository;

    private final AppUserConverter appUserConverter;
    private final VolunteerConverter volunteerConverter;

    private final HelpSeekerConverter helpSeekerConverter;
    private final RatingConverter ratingConverter;

    public ProfileService(VolunteerRepository volunteerRepository, AppUserRepository appUserRepository, HelpSeekerRepository helpSeekerRepository, RatingRepository ratingRepository, AppUserConverter appUserConverter, VolunteerConverter volunteerConverter, HelpSeekerConverter helpSeekerConverter, RatingConverter ratingConverter) {
        this.volunteerRepository = volunteerRepository;
        this.appUserRepository = appUserRepository;
        this.helpSeekerRepository = helpSeekerRepository;
        this.ratingRepository = ratingRepository;
        this.appUserConverter = appUserConverter;
        this.volunteerConverter = volunteerConverter;
        this.helpSeekerConverter = helpSeekerConverter;
        this.ratingConverter = ratingConverter;
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

    public List<RatingDTO> getAllRatingsForHelpSeeker(Long helpSeekerId) {
        List<Rating> ratings = ratingRepository.findByHelpSeekerId(helpSeekerId);
        List<RatingDTO> ratingDTOs = new ArrayList<>();

        for (Rating rating : ratings) {
            RatingDTO ratingDTO = ratingConverter.convertRatingToRatingDTO(rating);
            ratingDTOs.add(ratingDTO);
        }

        return ratingDTOs;
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

    public void saveRating(Long helpSeekerId, RatingDTO ratingDTO) {
        HelpSeeker helpSeeker = helpSeekerRepository.findById(helpSeekerId)
                .orElseThrow(() -> new EntityNotFoundException("HelpSeeker not found with id: " + helpSeekerId));

        if (ratingRepository.findOneByHelpSeekerAndVolunteer(helpSeeker, getCurrentVolunteer()).isPresent()) {
            throw new EntityExistsException("You can not give rating more than one time");

        } else {
            Rating rating = new Rating();
            rating.setVolunteer(getCurrentVolunteer());
            rating.setHelpSeeker(helpSeeker);
            rating.setRating(ratingDTO.getRating());
            rating.setComment(ratingDTO.getComment());

            helpSeeker.getRatings().add(rating);
            updateHelpSeekerRatingStats(helpSeeker);
            ratingRepository.save(rating);

            helpSeekerRepository.save(helpSeeker);
        }
    }

    private void updateHelpSeekerRatingStats(HelpSeeker helpSeeker) {
        int totalRatings = helpSeeker.getRatings().size();
        double averageRating = helpSeeker.getRatings().stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0.0);

        averageRating = Math.ceil(averageRating * 2) / 2.0;
        helpSeeker.setTotalRatings(totalRatings);
        helpSeeker.setAverageRating(averageRating);
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


    public Volunteer getCurrentVolunteer() {
        AppUser currentUser = UserHelper.getCurrentUser();
        Optional<Volunteer> oVolunteer = volunteerRepository.findByAppUser(currentUser);
        if (oVolunteer.isPresent()) {
            return oVolunteer.get();
        }
        throw new EntityNotFoundException();
    }

    public HelpSeeker getCurrentHelpSeeker() {
        AppUser currentUser = UserHelper.getCurrentUser();
        Optional<HelpSeeker> oHelpSeeker = helpSeekerRepository.findByAppUser(currentUser);
        if (oHelpSeeker.isPresent()) {
            return oHelpSeeker.get();
        }
        throw new EntityNotFoundException();
    }

}
