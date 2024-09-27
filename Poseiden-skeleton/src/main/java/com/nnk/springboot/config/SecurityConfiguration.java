package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    private final LoginSuccess successHandler;

    /**
     * Configure the login security with our custom login Service and authentification
     *
     * @param customUserDetailsService customUserDetailsService
     * @param successHandler successHandler
     */
    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, LoginSuccess successHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    /**
     * Manage the right to authentication
     *
     * @param http http
     * @param bCryptPasswordEncoder bCryptPasswordEncoder
     * @return authenticationManagerBuilder.build()
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    /**
     * Secure the http access
     *
     * @param http HttpSecurity
     * @return http
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers( "/", "/login").permitAll()
                .requestMatchers("/user/*").hasAuthority("ADMIN")
                //Authentication request parameters
                .anyRequest().authenticated()

                )
                .formLogin((formLogin) -> formLogin
                        .defaultSuccessUrl("/bidList/list", true)
                        .successHandler(successHandler).permitAll()
                )
                //logout
                .logout((logout) -> logout.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"))
                        //Definition of the default logout success URL
                        .logoutSuccessUrl("/")
                        //Invalidate the current HTTP session and its cookies
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                //In case of exception of an access denied
                .exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/app/error"));

        return http.build();
    }

    /**
     * Bcrypt uses hash algorithm to store password
     *
     * @return passwordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Function for allowing the loading of the static resources
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true)
                .ignoring()
                .requestMatchers("/css/**");
    }
}
