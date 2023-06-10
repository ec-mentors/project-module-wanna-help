package project.wanna_help.profile.communication.dto;

import project.wanna_help.profile.persistence.domain.Volunteer;

public class RatingDTO {

    private int rating;
    private String comment;


    public RatingDTO(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public RatingDTO() {
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
