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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private DataSource dataSource;

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
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, 'true' as enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
    }

    /**
     * This method controls the access to the different URLs based on the authenticated user<br>
     * The login page is accessible to all<br>
     * <b>There is additional filtering done in UserController</b> to give users access to their own update page
     * and to give admins access to every user's update page
     * (see UserController.showUpdateForm() and UserController.updateUser()<br>
     * The error page is accessible to all authenticated users<br>
     * The user part of the app is accessible only to admins<br>
     * The rest of the app is accessible only to users
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( auth -> { auth
                        .requestMatchers("login", "/app-logout").permitAll()
                        .requestMatchers("user/update/**", "error").authenticated()
                        .requestMatchers("user/**", "admin/home", "secure/article-details").hasAuthority("ADMIN")
                        .anyRequest().hasAuthority("USER");
                })
                .formLogin(withDefaults());
        http.logout((logout) -> logout
                .logoutUrl("/app-logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true));
        return http.build();
    }
}
