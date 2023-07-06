package project.wanna_help.activity.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.activity.persistence.domain.ApplicationStatus;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {


    List<Application> findByVolunteerAndApplicationStatus(Volunteer volunteer, ApplicationStatus applicationStatus);

    Optional<Application> findByIdAndVolunteerAndApplicationStatus(Long id, Volunteer volunteer, ApplicationStatus applicationStatus);

    Optional<Application> findByIdAndApplicationStatusAndActivity_HelpSeeker(Long id, ApplicationStatus applicationStatus, HelpSeeker helpSeeker);

    List<Application> findByApplicationStatusAndVolunteerAndActivity_HelpSeeker(ApplicationStatus applicationStatus, Volunteer volunteer, HelpSeeker helpSeeker);

    Optional<Application> findByVolunteerAndActivity(Volunteer volunteer, Activity activity);

    List<Application> findByVolunteerId(Long id);


    List<Application> findByActivity(Activity activity);

    List<Application> findByVolunteerAndApplicationStatusIn(Volunteer currentVolunteer, List<ApplicationStatus> applicationStatuses);


    List<Application> findByVolunteerAndActivityAndApplicationStatusIn(Volunteer volunteer, Activity activity, List<ApplicationStatus> pending);

    List<Application> findByApplicationStatusAndActivity_HelpSeeker(ApplicationStatus applicationStatus, HelpSeeker helpSeeker);
}

