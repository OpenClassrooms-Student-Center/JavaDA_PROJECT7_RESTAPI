package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.services.UserDetailsServiceImpl;

/*
 * Spring Security Configuration for HTTP request filter
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

	http.csrf().disable().authorizeRequests().antMatchers("/").permitAll().antMatchers("/home").permitAll().and()
		.formLogin().loginProcessingUrl("/j_spring_security_check").defaultSuccessUrl("/home", true)
		.failureUrl("/login?error=true").permitAll().and().exceptionHandling().accessDeniedPage("/403").and()
		.rememberMe().key("uniqueAndSecret").and()
		.logout(logout -> logout.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID"));
    }

//    .loginPage("/login")
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
	return new UserDetailsServiceImpl();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	authProvider.setUserDetailsService(userDetailsService());
	authProvider.setPasswordEncoder(passwordEncoder());

	return authProvider;
    }
}
