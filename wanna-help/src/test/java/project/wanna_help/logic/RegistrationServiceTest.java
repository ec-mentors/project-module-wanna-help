package project.wanna_help.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.domain.UserRole;
import project.wanna_help.persistence.repository.AppUserRepository;

import javax.validation.ConstraintViolationException;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RegistrationServiceTest {

    @Autowired
    RegistrationService registrationService;

    @MockBean
    AppUserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    SecurityFilterChain securityFilterChain;


    static Stream<Arguments> parameters() {
        return Stream.of( //correct
                Arguments.of("right@email.com", "rightUsername1", "password1", UserRole.VOLUNTEER, "full name", true),
                //password
                Arguments.of("right@email.com", "rightUsername1", "password", UserRole.VOLUNTEER, "full name", false),
                Arguments.of("right@email.com", "rightUsername1", "123456", UserRole.VOLUNTEER, "full name", false),
                Arguments.of("right@email.com", "rightUsername1", "short", UserRole.VOLUNTEER, "full name", false),
                Arguments.of("right@email.com", "rightUsername1", null, UserRole.VOLUNTEER, "full name", false),
                //email
                Arguments.of("", "rightUsername1", "password1", UserRole.VOLUNTEER, "full name", false),
                Arguments.of(null, "rightUsername1", "password1", UserRole.VOLUNTEER, "full name", false),
                Arguments.of("rightÄTemail.com", "rightUsername1", "password1", UserRole.VOLUNTEER, "full name", false),
                //username
                Arguments.of("right@email.com", null, "password1", UserRole.VOLUNTEER, "full name", false),
                Arguments.of("right@email.com", "", "password1", UserRole.VOLUNTEER, "full name", false),
                Arguments.of("right@email.com", "usernamewith???", "password1", UserRole.VOLUNTEER, "full name", false),
                //role
                Arguments.of("right@email.com", "rightUsername1", "password1", null, "full name", false),
                //fullname
                Arguments.of("right@email.com", "rightUsername1", "password1", UserRole.VOLUNTEER, "", false),
                Arguments.of("right@email.com", "rightUsername1", "password1", UserRole.VOLUNTEER, null, false)

        );
    }


    @ParameterizedTest
    @MethodSource("parameters")
    void register(String email, String username, String password, UserRole role, String fullName, boolean correct) {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setRole(role);
        appUser.setFullName(fullName);
        String encodedPassword = "1238348384";

        Mockito.when(userRepository.save(appUser)).thenReturn(appUser);
        Mockito.when(passwordEncoder.encode(appUser.getPassword())).thenReturn(encodedPassword);

        if (correct) {
            appUser = registrationService.register(appUser);
            Mockito.verify(userRepository).save(appUser);
            Mockito.verify(passwordEncoder).encode(password);
        } else {
            AppUser finalAppUser = appUser;
            Assertions.assertThrows(ConstraintViolationException.class, () -> registrationService.register(finalAppUser));
        }
    }
}