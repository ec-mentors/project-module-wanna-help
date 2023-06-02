package project.wanna_help.registration.communication.endpoint;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import project.wanna_help.registration.logic.LoginService;
import project.wanna_help.registration.persistence.domain.AppUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginEndpointTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    LoginService loginService;

    String url = "/login";

    @Test
    void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("Tomi84");
        loginDto.setPassword("tomtom84");
        Mockito.when(loginService.login(loginDto.getUsernameOrEmail(), loginDto.getPassword())).thenReturn(Optional.of(new AppUser()));

        testRestTemplate.postForObject(url, loginDto, String.class);
        Mockito.verify(loginService).login(loginDto.getUsernameOrEmail(), loginDto.getPassword());

    }

}