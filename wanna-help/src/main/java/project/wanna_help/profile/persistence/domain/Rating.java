package project.wanna_help.profile.persistence.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private HelpSeeker helpSeeker;
    @ManyToOne
    private Volunteer volunteer;
    @Size(min = 1, max = 5)
    private int rating;
    public Rating() {
    }

    public Rating(HelpSeeker helpSeeker, Volunteer volunteer, int rating) {
        this.helpSeeker = helpSeeker;
        this.volunteer = volunteer;
        this.rating = rating;
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
