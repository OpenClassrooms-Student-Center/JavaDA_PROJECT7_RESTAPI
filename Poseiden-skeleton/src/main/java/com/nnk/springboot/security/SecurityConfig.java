package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserPrincipal userPrincipal;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

            auth.inMemoryAuthentication()
                    .withUser("springUser").password(passwordEncoder().encode("spring123")).roles("USER")
                    .and()
                    .withUser("springAdmin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER");

//            auth.jdbcAuthentication().withUser(userPrincipal.getUsername()).password(passwordEncoder().encode(userPrincipal.getPassword())).roles("USER")
//                    .and()
//                    .withUser(userPrincipal.getUsername()).password(passwordEncoder().encode(userPrincipal.getPassword())).roles("ADMIN", "USER");


        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/home", "/", "/403").permitAll()
                    .antMatchers("/user/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .oauth2Login();
        }



    }
