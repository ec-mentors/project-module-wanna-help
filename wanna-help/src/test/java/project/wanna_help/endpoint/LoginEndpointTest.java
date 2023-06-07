package project.wanna_help.endpoint;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import project.wanna_help.logic.RegistrationService;
import project.wanna_help.persistence.domain.UserRole;
import project.wanna_help.persistence.dto.LoginDto;
import project.wanna_help.logic.LoginService;
import project.wanna_help.persistence.domain.AppUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class LoginEndpointTest {
//
//    @Autowired
//    TestRestTemplate testRestTemplate;
//
//    @MockBean
//    LoginService loginService;
//
//    String url = "/users/login";
//
//    @Test
//    void login() {
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUsernameOrEmail("Tomi84");
//        loginDto.setPassword("tomtom84");
//        when(loginService.login(loginDto.getUsernameOrEmail(), loginDto.getPassword())).thenReturn(Optional.of(new AppUser()));
//
//        testRestTemplate.postForObject(url, loginDto, String.class);
//        Mockito.verify(loginService).login(loginDto.getUsernameOrEmail(), loginDto.getPassword());
//
//    }
//
//
//}
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginEndpointTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    RegistrationService registrationService;


    @Before
    public void registration() {

        AppUser appUser = new AppUser();
        appUser.setEmail("testemail");
        appUser.setUsername("testuser");
        appUser.setPassword("testpassword");
        appUser.setRole(UserRole.VOLUNTEER);
        appUser.setFullName("test test");


        registrationService.register(appUser);

    }

    @Test
    void login_valid() {

        ResponseEntity<String> result = testRestTemplate.withBasicAuth("testuser", "testpassword")
                .postForEntity("/users/login", null, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }


}
