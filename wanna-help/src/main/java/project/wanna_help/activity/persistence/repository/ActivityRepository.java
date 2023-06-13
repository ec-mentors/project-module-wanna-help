package project.wanna_help.activity.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.ActivityStatus;
import project.wanna_help.profile.persistence.domain.HelpSeeker;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByActivityStatus(ActivityStatus activityStatus);

    Optional<Activity> findByIdAndActivityStatus(Long id, ActivityStatus activityStatus);


    List<Activity> findByActivityStatusAndHelpSeeker(ActivityStatus activityStatus, HelpSeeker helpSeeker);

    Optional<Activity> findByIdAndHelpSeeker(Long id, HelpSeeker helpSeeker);

    Optional<Activity> findByIdAndHelpSeekerAndActivityStatus(Long id, HelpSeeker helpSeeker,ActivityStatus activityStatus);



}
