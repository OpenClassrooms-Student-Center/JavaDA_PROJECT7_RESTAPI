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

//    @Autowired
//    protected void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
//	auth.authenticationProvider(authenticationProvider());
//    }

    // @todo add oAuth
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	return http.csrf().disable().authorizeRequests().antMatchers("/bidList/**").hasAnyAuthority("USER", "ADMIN")
		.antMatchers("/curvePoint/**").hasAnyAuthority("USER", "ADMIN").antMatchers("/rating/**")
		.hasAnyAuthority("USER", "ADMIN").antMatchers("/ruleName/**").hasAnyAuthority("USER", "ADMIN")
		.antMatchers("/trade/**").hasAnyAuthority("USER", "ADMIN").antMatchers("/").permitAll().and()
		.formLogin().loginProcessingUrl("/j_spring_security_check").defaultSuccessUrl("/bidList/list", true)
		.failureUrl("/login?error=true").permitAll().and().exceptionHandling().accessDeniedPage("/403").and()
		.rememberMe().key("uniqueAndSecret").and()
		.logout(logout -> logout.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID"))
		.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	authProvider.setUserDetailsService(userDetailsService());
//	authProvider.setPasswordEncoder(passwordEncoder());
//
//	return authProvider;
//    }
}
