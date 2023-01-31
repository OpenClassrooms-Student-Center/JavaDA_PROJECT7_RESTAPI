package com.nnk.springboot.configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
    log.info("la security chain");
     http
             .authorizeHttpRequests()
             .antMatchers("/").permitAll()
             .antMatchers("/user/add").permitAll()
             .antMatchers("/user/list").permitAll()
             .antMatchers("/user/validate").permitAll()
             .anyRequest().authenticated()
             .and()
             .formLogin()
             .and()
             .logout()
             .logoutUrl("perform_logout")
             .logoutSuccessUrl("/");

    }

}
