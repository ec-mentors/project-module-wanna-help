package project.wanna_help.registration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.wanna_help.registration.persistence.repository.AppUserRepository;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

//    private final CustomLoginFailureHandler failureHandler;

//    public SecurityConfiguration(CustomLoginFailureHandler failureHandler) {
//        this.failureHandler = failureHandler;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(POST, "/users").permitAll()
                .antMatchers(POST, "/users/password-reset-link").permitAll()
                .antMatchers(POST, "/users/password-reset/{token}").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()  // ------> SIGNOUT!!
                .logoutUrl("/users/signout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
//                .formLogin() // FIXME not counting up the fail login trys
//                .failureHandler(failureHandler)
//                .and()
                .httpBasic();
        return http.build();
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    UserDetailsService userDetailsService(AppUserRepository appUserRepository) {
        return identifier -> appUserRepository.findOneByUsernameOrEmail(identifier, identifier)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(identifier));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
