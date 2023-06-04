package project.wanna_help.registration.communication.endpoint;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import project.wanna_help.persistence.dto.LoginDto;
import project.wanna_help.logic.LoginService;
import project.wanna_help.persistence.domain.AppUser;

import java.util.Optional;

import static org.mockito.Mockito.when;

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
        when(loginService.login(loginDto.getUsernameOrEmail(), loginDto.getPassword())).thenReturn(Optional.of(new AppUser()));

        testRestTemplate.postForObject(url, loginDto, String.class);
        Mockito.verify(loginService).login(loginDto.getUsernameOrEmail(), loginDto.getPassword());

    }


}