package project.wanna_help.profile.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.activity.persistence.domain.Activity;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.profile.persistence.domain.VisibilityStatus;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Optional<Volunteer> findByAppUser(AppUser appUser);

    Optional<Volunteer> findById(Long id);

    List<Volunteer> findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus visibilityStatus);


    Optional<Volunteer>findByApplications_Activity(Activity activity);




}
