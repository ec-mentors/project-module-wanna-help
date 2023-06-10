package project.wanna_help.profile.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.profile.persistence.domain.HelpSeeker;
import project.wanna_help.profile.persistence.domain.Rating;
import project.wanna_help.profile.persistence.domain.Volunteer;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByHelpSeekerId(Long helpSeekerId);

    Optional<Rating> findOneByHelpSeekerAndVolunteer(HelpSeeker helpSeeker, Volunteer volunteer);

}
