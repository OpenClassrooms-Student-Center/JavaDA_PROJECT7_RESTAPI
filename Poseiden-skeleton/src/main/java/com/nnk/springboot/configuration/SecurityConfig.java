package com.nnk.springboot.configuration;
import com.nnk.springboot.service.MyUserDetailsService;
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
     http
             .authorizeHttpRequests()
             .antMatchers("/").permitAll()
             .antMatchers("/user/add").permitAll()
             .antMatchers("/user/list").permitAll()
             .antMatchers("/bidList/list").permitAll()
             .antMatchers("/bidList/add").permitAll()
             .antMatchers("/bidList/validate").permitAll()
             .antMatchers("/bidList/delete/{id}").permitAll()
             .antMatchers("/bidList/update/{id}").permitAll()
             .antMatchers("/user/delete/{id}").permitAll()
             .antMatchers("/user/validate").permitAll()
             .antMatchers("/curvePoint/list").permitAll()
             .antMatchers("/curvePoint/add").permitAll()
             .antMatchers("/curvePoint/validate").permitAll()
             .antMatchers("/curvePoint/delete/{id}").permitAll()
             .antMatchers("/curvePoint/update/{id}").permitAll()
             .antMatchers("/rating/list").permitAll()
             .antMatchers("/rating/add").permitAll()
             .antMatchers("/rating/validate").permitAll()
             .antMatchers("/rating/update/{id}").permitAll()
             .antMatchers("/trade/list").permitAll()
             .antMatchers("/trade/add").permitAll()
             .antMatchers("/trade/validate").permitAll()
             .antMatchers("/trade/update/{id}").permitAll()
             .antMatchers("/trade/delete/{id}").permitAll()
             .antMatchers("/ruleName/list").permitAll()
             .antMatchers("/ruleName/add").permitAll()
             .antMatchers("/ruleName/validate").permitAll()
             .antMatchers("/ruleName/update/{id}").permitAll()
             .antMatchers("/ruleName/delete/{id}").permitAll()
             .anyRequest().authenticated()
             .and()
             .formLogin()
             .and()
             .logout()
             .logoutUrl("perform_logout")
             .logoutSuccessUrl("/");

    }

}
