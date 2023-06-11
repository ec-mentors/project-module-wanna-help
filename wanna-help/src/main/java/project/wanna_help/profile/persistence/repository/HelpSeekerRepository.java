package project.wanna_help.profile.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.profile.persistence.domain.HelpSeeker;

import java.util.Optional;

public interface HelpSeekerRepository extends JpaRepository<HelpSeeker, Long> {

    Optional<HelpSeeker> findByAppUser(AppUser appUser);

    Optional<HelpSeeker> findById(Long id);


}
