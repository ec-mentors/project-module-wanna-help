package project.wanna_help.activity.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.activity.persistence.domain.Application;
import project.wanna_help.activity.persistence.domain.ApplicationStatus;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {


    List<Application> findByVolunteerAndApplicationStatus(Volunteer volunteer, ApplicationStatus applicationStatus);


}
