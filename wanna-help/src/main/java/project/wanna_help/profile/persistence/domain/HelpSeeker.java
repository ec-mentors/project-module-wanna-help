package project.wanna_help.profile.persistence.domain;

import project.wanna_help.Activity.Activity;
import project.wanna_help.persistence.domain.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class HelpSeeker {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
     @MapsId
    private AppUser appUser;

    //calculate actual rating from ratings
    @OneToMany(mappedBy = "helpSeeker", cascade = CascadeType.ALL, orphanRemoval = true)
    //bidirectional and it says who is the leader.
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Activity> applications;


    public HelpSeeker() {
    }

    public HelpSeeker(AppUser appUser, List<Rating> ratings, Set<Activity> applications) {
        this.appUser = appUser;
        this.ratings = ratings;
        this.applications = applications;
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

    public Set<Activity> getApplications() {
        return applications;
    }

    public void setApplications(Set<Activity> applications) {
        this.applications = applications;
    }
}
