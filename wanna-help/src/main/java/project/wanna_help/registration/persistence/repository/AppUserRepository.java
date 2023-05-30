package project.wanna_help.registration.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.registration.persistence.domain.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findOneByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);

}
