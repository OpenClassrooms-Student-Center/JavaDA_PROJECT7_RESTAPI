package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Setups security filters
     * Endpoint user/add is avaible without authentication
     * All other endpoints need authentication
     * Endpoints user/list, user/update, user/delete are avaible only for Admin
     * All endpoints /update/* and /delete/* are avaible only for Admin
     * The other endpoints are avaible for both Admin and Users
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/user/add").permitAll();
            auth.requestMatchers("/user/validate").permitAll();

            auth.requestMatchers("/user/list").hasRole("ADMIN");
            auth.requestMatchers("/user/update/*").hasRole("ADMIN");
            auth.requestMatchers("/user/delete/*").hasRole("ADMIN");

            auth.requestMatchers("/bidList/update/*").hasRole("ADMIN");
            auth.requestMatchers("/bidList/delete/*").hasRole("ADMIN");

            auth.requestMatchers("/curvePoint/update/*").hasRole("ADMIN");
            auth.requestMatchers("/curvePoint/delete/*").hasRole("ADMIN");

            auth.requestMatchers("/rating/update/*").hasRole("ADMIN");
            auth.requestMatchers("/rating/delete/*").hasRole("ADMIN");

            auth.requestMatchers("/ruleName/update/*").hasRole("ADMIN");
            auth.requestMatchers("/ruleName/delete/*").hasRole("ADMIN");

            auth.requestMatchers("/trade/update/*").hasRole("ADMIN");
            auth.requestMatchers("/trade/delete/*").hasRole("ADMIN");

            auth.anyRequest().authenticated();

        }).formLogin(Customizer.withDefaults()).build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Makes Spring Security using CustomUserDetailsService to authenticate users
     * @param http
     * @param bCryptPasswordEncoder
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }



}
