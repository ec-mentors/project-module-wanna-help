package project.wanna_help.appuser.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.appuser.persistence.domain.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findOneByUsernameOrEmail(String username, String email);

    Optional<AppUser> findByEmail(String email);
    boolean existsByUsername(String username);

}
