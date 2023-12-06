package com.nnk.springboot.config;

import com.nnk.springboot.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    /**
     * the persistence layer, defined in application.properties
     */
    @Autowired
    private DataSource dataSource;

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
     * Spring Security expects the table UserDetails is mapped to to have a column containing a boolean
     * indicating if the user is enabled, but the table "users" in the database doesn't have one.<br>
     * This is circumvented by the use "'true' as enabled" in the sql query.
     * @param auth
     * @throws Exception
     */

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, 'true' as enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
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
        http.authorizeHttpRequests( auth -> { auth
                        .requestMatchers("login", "/app-logout").permitAll()
                        .requestMatchers("user/update/**", "error", "/").authenticated()
                        .requestMatchers("user/**", "home/admin", "secure/article-details").hasAuthority("ADMIN")
                        .anyRequest().hasAuthority("USER");
                })
                .formLogin((login) -> login.successForwardUrl("/"));
        http.logout((logout) -> logout
                .logoutUrl("/app-logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true));
        return http.build();
    }
}
