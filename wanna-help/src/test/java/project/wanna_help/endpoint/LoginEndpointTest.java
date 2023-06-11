package project.wanna_help.endpoint;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.wanna_help.logic.RegistrationService;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.domain.UserRole;
import project.wanna_help.persistence.repository.AppUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginEndpointTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @MockBean
    RegistrationService registrationService;
    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    AppUser appUser = new AppUser();
    String password = "testpassword123";
    String username= "testuser1234";

    @BeforeEach
    public void registration() {
        appUser.setEmail("testemail@email.com");
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setRole(UserRole.VOLUNTEER);
        appUser.setFullName("test test");
    }

    @Test
    void login_valid() {
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);
        Mockito.when(appUserRepository.findOneByUsernameOrEmail(username, username)).thenReturn(Optional.of(appUser));

        ResponseEntity<String> result = testRestTemplate.withBasicAuth(username, password)
                .postForEntity("/users/login", null, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void login_inValid_Password() {
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);
        Mockito.when(appUserRepository.findOneByUsernameOrEmail(username, username)).thenReturn(Optional.of(appUser));

        ResponseEntity<String> result = testRestTemplate.withBasicAuth(username, "invalid123")
                .postForEntity("/users/login", null, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());

    }

    @Test
    void login_inValid_Username() {
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);
        Mockito.when(appUserRepository.findOneByUsernameOrEmail(username, username)).thenReturn(Optional.of(appUser));

        ResponseEntity<String> result = testRestTemplate.withBasicAuth("invalid", password)
                .postForEntity("/users/login", null, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());

    }
    @Test
    void login_inValid_ROLE() {
        appUser.setRole(null);
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);
        Mockito.when(appUserRepository.findOneByUsernameOrEmail(username, username)).thenReturn(Optional.of(appUser));

        ResponseEntity<String> result = testRestTemplate.withBasicAuth(username, password)
                .postForEntity("/users/login", null, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());

    }


}
