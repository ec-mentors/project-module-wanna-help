package project.wanna_help.profile.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Optional<Volunteer> findByAppUser(AppUser appUser);

    Optional<Volunteer> findById(Long id);


}
