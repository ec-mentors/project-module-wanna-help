package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.activity.persistence.domain.ApplicationStatus;
import project.wanna_help.activity.persistence.repository.ActivityRepository;
import project.wanna_help.activity.persistence.repository.ApplicationRepository;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Rating;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.RatingRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingConverter ratingConverter;

    private final HelpSeekerRepository helpSeekerRepository;
    private final ApplicationRepository applicationRepository;
    private final UserHelper userHelper;

    public RatingService(RatingRepository ratingRepository, RatingConverter ratingConverter, HelpSeekerRepository helpSeekerRepository, ApplicationRepository applicationRepository, UserHelper userHelper) {
        this.ratingRepository = ratingRepository;
        this.ratingConverter = ratingConverter;
        this.helpSeekerRepository = helpSeekerRepository;
        this.applicationRepository = applicationRepository;
        this.userHelper = userHelper;
    }

    public void saveRating(Long helpSeekerId, RatingDTO ratingDTO) {
        HelpSeeker helpSeeker = helpSeekerRepository.findById(helpSeekerId)
                .orElseThrow(() -> new EntityNotFoundException("HelpSeeker not found with id: " + helpSeekerId));

        Volunteer currentVolunteer = userHelper.getCurrentVolunteer();

        var applications = applicationRepository.findByApplicationStatusAndVolunteerAndActivity_HelpSeeker(ApplicationStatus.DONE, currentVolunteer, helpSeeker);

        applications.sort(Comparator.comparing(Application::getTimeStamp).reversed());


        var previousRating = ratingRepository.findOneByHelpSeekerAndVolunteer(helpSeeker, userHelper.getCurrentVolunteer());
        if (applications.isEmpty()) {
            throw new EntityNotFoundException("You cannot add a rating before your application is done");
        }
        Application latestApplication = applications.get(0);
        if (previousRating.isPresent() && latestApplication.getTimeStamp().isBefore(previousRating.get().getRatingDate())) {
            throw new EntityExistsException("You cannot give a rating more than once. You can update your rating after finishing another application with the same helpSeeker");

        } else {
            previousRating.ifPresent(rating -> {
                helpSeeker.getRatings().remove(rating);
                ratingRepository.delete(rating);
            });

            Rating rating = new Rating();
            rating.setVolunteer(userHelper.getCurrentVolunteer());
            rating.setHelpSeeker(helpSeeker);
            rating.setRating(ratingDTO.getRating());
            rating.setComment(ratingDTO.getComment());
            LocalDateTime currentDateTime = LocalDateTime.now();
            rating.setRatingDate(currentDateTime);

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

    public List<RatingDTO> getAllRatingsForHelpSeeker(Long helpSeekerId) {
        HelpSeeker helpSeeker = helpSeekerRepository.findById(helpSeekerId)
                .orElseThrow(() -> new EntityNotFoundException("HelpSeeker not found with id: " + helpSeekerId));

        List<Rating> ratings = ratingRepository.findByHelpSeekerId(helpSeeker.getId());
        List<RatingDTO> ratingDTOs = new ArrayList<>();

        ratings.sort(Comparator.comparing(Rating::getRatingDate).reversed());

        for (Rating rating : ratings) {
            RatingDTO ratingDTO = ratingConverter.convertRatingToRatingDTO(rating);
            ratingDTOs.add(ratingDTO);
        }

        return ratingDTOs;
    }
}
