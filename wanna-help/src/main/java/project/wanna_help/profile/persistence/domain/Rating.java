package project.wanna_help.profile.persistence.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private HelpSeeker helpSeeker;
    @ManyToOne(fetch = FetchType.LAZY)
    private Volunteer volunteer;

    private LocalDateTime ratingDate;
    @Range(min = 1, max = 5, message = "rating should be integer between 1 and 5")
    private int rating;
    private String comment;

    public Rating(HelpSeeker helpSeeker, Volunteer volunteer, LocalDateTime ratingDate, int rating, String comment) {
        this.helpSeeker = helpSeeker;
        this.volunteer = volunteer;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating(HelpSeeker helpSeeker, Volunteer volunteer, int rating) {
        this.helpSeeker = helpSeeker;
        this.volunteer = volunteer;
        this.rating = rating;
    }

    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HelpSeeker getHelpSeeker() {
        return helpSeeker;
    }

    public void setHelpSeeker(HelpSeeker helpSeeker) {
        this.helpSeeker = helpSeeker;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

