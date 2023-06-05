package project.wanna_help.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.domain.PasswordResetToken;
import project.wanna_help.persistence.repository.AppUserRepository;
import project.wanna_help.persistence.repository.PasswordResetTokenRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgottenPasswordService {
    private final AppUserRepository userRepository;

    private final EmailRedirector redirector;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final long expirationHours;

    public ForgottenPasswordService(AppUserRepository userRepository,
                                    EmailRedirector redirector,
                                    PasswordResetTokenRepository passwordResetTokenRepository,
                                    PasswordEncoder passwordEncoder,
                                    @Value("${newpassword.HoursToExpire}") long expirationHours) {
        this.userRepository = userRepository;
        this.redirector = redirector;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.expirationHours = expirationHours;
    }

    public void generatePasswordResetLink(String nameOrEmail) {
        Optional<AppUser> optionalUser = userRepository.findOneByUsernameOrEmail(nameOrEmail, nameOrEmail);
        if (optionalUser.isPresent()) {
            AppUser appUser = optionalUser.get();
            String useremail = appUser.getEmail();
            String link = createPasswordResetTokenForUser(appUser);
            redirector.redirectEMail(useremail, link);
        } else {
            throw new EntityNotFoundException("unknown email or user");
        }
    }

    private Date calculateExpirationDate() {
        Date now = new Date();
        long expirationTimestamp = now.getTime() + (expirationHours * 60 * 60 * 1000); //milliseconds: 1000x60x60x24 =24h
        return new Date(expirationTimestamp);
    }


    private String createPasswordResetTokenForUser(AppUser user) {
        String token = UUID.randomUUID().toString();
        Date expirationDate = calculateExpirationDate();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, expirationDate);
        passwordResetTokenRepository.save(passwordResetToken);
        return "http://localhost:9100/users/password-reset/" + token;
    }

    public void resetPassword(String token, String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new IllegalArgumentException("passwords don't match");
        }

        Optional<PasswordResetToken> optionalPasswordResetToken = passwordResetTokenRepository.findByToken(token);
        if (!optionalPasswordResetToken.isPresent()) {
            throw new IllegalArgumentException("token is not valid");
        }
        PasswordResetToken passwordResetToken = optionalPasswordResetToken.get();
        if (passwordResetToken.getExpiryDate().before(new Date())) {
            throw new IllegalArgumentException("link is already expired, please request new");
        }
        AppUser user = passwordResetToken.getUser();
        String encodedPassword = passwordEncoder.encode(password1);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}
