package com.nnk.springboot.config;

import com.nnk.springboot.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * @return a CustomUserDetailsService that connects SpringSecurity UserDetails objects with domain User objects
     */

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * This method controls the access to the different URLs based on the authenticated user<br><br>
     * The login and logout page are accessible to all<br>
     * The error page is accessible to all authenticated users<br>
     * <b>There is additional filtering done in UserController</b> to give users access to their own update page
     * and to give admins access to every user's update page
     * (see UserController.showUpdateForm() and UserController.updateUser()<br>
     * The user part of the app, "home/admin" and "secure/article-details" are accessible only to admins<br>
     * The rest of the app is accessible only to users<br><br>
     * After a successful login request, every user is forwarded to the "/" url,
     * which is mapped onto the home() method of HomeController,
     * which will redirect the user according to its role.<br><br>
     * After logging out, the JSESSIONID is deleted, and the http session is invalidated.
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( auth -> auth
                .requestMatchers("user/update/**", "/").authenticated()
                .requestMatchers("user/**", "home/admin", "secure/article-details").hasAuthority("ADMIN")
                .anyRequest().hasAuthority("USER"));
        http.formLogin(
                (login) -> login
                        .successForwardUrl("/")
                        .permitAll());
        http.logout(
                (logout) -> logout
                        .logoutUrl("/app-logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll());
        return http.build();
    }
}
