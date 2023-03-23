package com.nnk.springboot.configuration;

import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfig  {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .authorizeRequests()


                .requestMatchers(request -> request.getRequestURI().equals("/user/add")).permitAll() // allow homePage without authentication
                .requestMatchers(request -> request.getRequestURI().equals("/bidList/list")).permitAll() // allow homePage without authentication
                .requestMatchers(request -> request.getRequestURI().equals("/user/validate")).permitAll() // allow homePage without authentication

                .requestMatchers(request -> request.getRequestURI().equals("/home")).permitAll() // allow homePage without authentication

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//cette méthode gère les requêtes http
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/resources/**",
                                "/css/**",
                                "/fonts/**",
                                "/img/**").permitAll()
                        .requestMatchers(request -> request.getRequestURI().equals("/user/add")).permitAll() // allow homePage without authentication
                        .requestMatchers(request -> request.getRequestURI().equals("/home")).permitAll() // allow homePage without authentication
                        .requestMatchers(request -> request.getRequestURI().equals("/login")).permitAll() // allow homePage without authentication
                        .anyRequest().authenticated()
                )

                .formLogin()
                    .loginPage("/login")
                    .permitAll()

                    .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home");
                   .and()
                .oauth2Login();
        http.authenticationProvider(authenticationProvider());
        return http.build();

    }*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)throws Exception{
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }
}
