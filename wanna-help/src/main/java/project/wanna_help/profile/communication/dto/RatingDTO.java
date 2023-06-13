package project.wanna_help.profile.communication.dto;


import java.time.Instant;
import java.time.LocalDateTime;

public class RatingDTO {

    private LocalDateTime ratingDate;
    private int rating;
    private String comment;

    public RatingDTO() {
    }

    public RatingDTO(LocalDateTime ratingDate, int rating, String comment) {
        this.ratingDate = ratingDate;
        this.rating = rating;
        this.comment = comment;
    }

    public LocalDateTime getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(LocalDateTime ratingDate) {
        this.ratingDate = ratingDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
