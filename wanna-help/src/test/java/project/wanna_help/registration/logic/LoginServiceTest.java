package project.wanna_help.registration.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.wanna_help.logic.LoginService;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.persistence.repository.AppUserRepository;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    SecurityFilterChain securityFilterChain;


    @Test
    public void testLogin_Success() {
        String usernameOrEmail = "john@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUsername("john1");
        appUser.setEmail("john@example.com");
        appUser.setPassword(encodedPassword);


        Mockito.when(appUserRepository.findOneByUsernameOrEmail(usernameOrEmail, usernameOrEmail))
                .thenReturn(Optional.of(appUser));
        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);


        Optional<AppUser> result = loginService.login(usernameOrEmail, password);
        Mockito.verify(appUserRepository).findOneByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        Mockito.verify(passwordEncoder).encode(password);

//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(appUser, Optional.of(result));

    }

}