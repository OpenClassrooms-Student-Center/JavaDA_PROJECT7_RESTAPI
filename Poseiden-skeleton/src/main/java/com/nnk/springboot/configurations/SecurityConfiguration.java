package com.nnk.springboot.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    private UserRepository userRepository;
    //securing routes by authority
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(req->req
        .requestMatchers("/admin/**").hasAuthority("ADMIN")
        .requestMatchers("/bidList/list").hasAnyAuthority("ADMIN","USER")
        .requestMatchers("/user/**").hasAnyAuthority("ADMIN")
        .requestMatchers("/bidList/**").hasAuthority("USER")
        .requestMatchers("/curvePoint/**").hasAuthority("USER")
        .requestMatchers("/rating/**").hasAuthority("USER")
        .requestMatchers("/ruleName/**").hasAuthority("USER")
        .requestMatchers("/trade/**").hasAuthority("USER")
        .requestMatchers("/css/**").permitAll()
        .requestMatchers("/").hasAuthority("USER")
        .anyRequest().denyAll()).formLogin(login->login.loginPage("/user/loginPage").permitAll()
        .loginProcessingUrl("/login").usernameParameter("username").defaultSuccessUrl("/"))
        .logout(logout->logout.logoutUrl("/app-logout").logoutSuccessUrl("/user/loginPage"))
        .authenticationManager(authenticationProvider());
        return http.build();
    }
    //configuring password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder();
    }
    //configuring authentication settings
    @Bean 
    public ProviderManager authenticationProvider() {
        
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService(userRepository));
        authenticationProviderList.add(daoAuthenticationProvider);
        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
        return providerManager;
    }
    //adding initial admin user
    //only admin can manage users
    @Bean
    public UserService userService(UserRepository userRepository) {
        User user = new User();
        user.setFullname("Admin");
        user.setUsername("admin");
        user.setPassword("AbC45678");
        user.setRole("ADMIN");
        userRepository.save(user);
        return new UserService(userRepository,user);
    } 
}
