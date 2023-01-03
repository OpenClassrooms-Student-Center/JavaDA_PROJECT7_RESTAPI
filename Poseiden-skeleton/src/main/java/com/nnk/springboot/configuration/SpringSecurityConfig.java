package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Spring Security Configuration for HTTP request filter
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	return http.csrf().disable().authorizeRequests().antMatchers("/bidList/**").authenticated()
		.antMatchers("/curvePoint/**").authenticated().antMatchers("/rating/**").authenticated()
		.antMatchers("/ruleName/**").authenticated().antMatchers("/trade/**").authenticated()
		.antMatchers("/user/**").authenticated().antMatchers("/").permitAll().and().oauth2Login()
		.defaultSuccessUrl("/bidList/list", true).failureUrl("/login?error=true").and().formLogin()
		.loginProcessingUrl("/j_spring_security_check").defaultSuccessUrl("/bidList/list", true)
		.failureUrl("/login?error=true").permitAll().and().exceptionHandling().accessDeniedPage("/403").and()
		.rememberMe().key("uniqueAndSecret").and().logout(logout -> logout.logoutSuccessUrl("/")
			.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID"))
		.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

}
