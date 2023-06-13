package project.wanna_help.profile.logic;

import org.hibernate.internal.HEMLogging;
import org.springframework.stereotype.Service;
import project.wanna_help.appuser.logic.UserHelper;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Rating;
import project.wanna_help.profile.persistence.repository.HelpSeekerRepository;
import project.wanna_help.profile.persistence.repository.RatingRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingConverter ratingConverter;

    private final HelpSeekerRepository helpSeekerRepository;

    private final UserHelper userHelper;

    public RatingService(RatingRepository ratingRepository, RatingConverter ratingConverter, HelpSeekerRepository helpSeekerRepository, UserHelper userHelper) {
        this.ratingRepository = ratingRepository;
        this.ratingConverter = ratingConverter;
        this.helpSeekerRepository = helpSeekerRepository;
        this.userHelper = userHelper;
    }

    public void saveRating(Long helpSeekerId, RatingDTO ratingDTO) {
        HelpSeeker helpSeeker = helpSeekerRepository.findById(helpSeekerId)
                .orElseThrow(() -> new EntityNotFoundException("HelpSeeker not found with id: " + helpSeekerId));

        if (ratingRepository.findOneByHelpSeekerAndVolunteer(helpSeeker, userHelper.getCurrentVolunteer()).isPresent()) {
            throw new EntityExistsException("You can not give rating more than one time");

        } else {
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
        List<Rating> ratings = ratingRepository.findByHelpSeekerId(helpSeekerId);
        List<RatingDTO> ratingDTOs = new ArrayList<>();

        ratings.sort(Comparator.comparing(Rating::getRatingDate).reversed());

        for (Rating rating : ratings) {
            RatingDTO ratingDTO = ratingConverter.convertRatingToRatingDTO(rating);
            ratingDTOs.add(ratingDTO);
        }

        return ratingDTOs;
    }
}
