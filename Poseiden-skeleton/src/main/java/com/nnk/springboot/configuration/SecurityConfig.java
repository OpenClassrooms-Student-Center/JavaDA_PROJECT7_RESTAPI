package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user")
                        .hasAuthority("USER")
                        .requestMatchers("/admin")
                        .hasAuthority("ADMIN")
                        .requestMatchers("css/bootstrap.min.css")
                        .permitAll()
                        .requestMatchers("/user/list")
                        .permitAll()
                        .requestMatchers("/trade/list")
                        .permitAll()
                        .requestMatchers("/app/login")
                        .permitAll()
                        .requestMatchers("/home")
                        .permitAll()
                        .requestMatchers("/user/**")
                        .permitAll()
                        .requestMatchers("/trade/**")
                        .permitAll()
                        .requestMatchers("/rating/**")
                        .permitAll()
                        .requestMatchers("/ruleName/**")
                        .permitAll()
                        .requestMatchers("/curvePoint/**")
                        .permitAll()
                        .requestMatchers("/bidList/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin()
                .loginPage("/")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }

}