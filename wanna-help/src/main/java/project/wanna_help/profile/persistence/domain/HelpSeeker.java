package project.wanna_help.profile.persistence.domain;

import project.wanna_help.persistence.domain.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HelpSeeker {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private AppUser appUser;

    //calculate actual rating from ratings

    @OneToMany
    private List<Rating> ratings = new ArrayList<>();

    private double averageRating;
    private int TotalRatings;

    public HelpSeeker(AppUser appUser, List<Rating> ratings, double averageRating, int totalRatings) {
        this.appUser = appUser;
        this.ratings = ratings;
        this.averageRating = averageRating;
        TotalRatings = totalRatings;
    }

    public HelpSeeker() {
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalRatings() {
        return TotalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        TotalRatings = totalRatings;
    }

    public HelpSeeker(AppUser appUser, List<Rating> ratings) {
        this.appUser = appUser;
        this.ratings = ratings;
    }

    public HelpSeeker(AppUser appUser) {
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }


}
