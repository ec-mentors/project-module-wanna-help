package project.wanna_help.profile.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.profile.persistence.domain.HelpSeeker;

public interface HelpSeekerRepository extends JpaRepository<HelpSeeker,Long> {

}
