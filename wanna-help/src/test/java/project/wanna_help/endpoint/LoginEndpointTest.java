package project.wanna_help.endpoint;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import project.wanna_help.logic.RegistrationService;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.domain.UserRole;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginEndpointTest {


    @Autowired
    TestRestTemplate testRestTemplate;
    @MockBean
    RegistrationService registrationService;


    @Before
    public void registration() {

        AppUser appUser = new AppUser();
        appUser.setEmail("testemail@email.com");
        appUser.setUsername("testuser123");
        appUser.setPassword("testpassword123");
        appUser.setRole(UserRole.VOLUNTEER);
        appUser.setFullName("test test");


        registrationService.register(appUser);

    }

    @Test
    void login_valid() {

        ResponseEntity<String> result = testRestTemplate.withBasicAuth("testuser123", "testpassword123")
                .postForEntity("/users/login", null, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }


}
