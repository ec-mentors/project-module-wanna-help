package project.wanna_help.appuser.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wanna_help.appuser.persistence.domain.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);
}
