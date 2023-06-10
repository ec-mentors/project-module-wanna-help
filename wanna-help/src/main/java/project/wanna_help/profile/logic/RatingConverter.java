package project.wanna_help.profile.logic;

import org.springframework.stereotype.Service;
import project.wanna_help.profile.communication.dto.RatingDTO;
import project.wanna_help.profile.persistence.domain.Rating;

@Service
public class RatingConverter {
    public RatingDTO convertRatingToRatingDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setComment(rating.getComment());
        ratingDTO.setRating(rating.getRating());
        return ratingDTO;
    }
}
