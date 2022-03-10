package com.nnk.springboot.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "/login").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/user/**").hasAuthority("ADMIN")
				.antMatchers("/app/**").hasAuthority("ADMIN")
				.antMatchers("/admin/home").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().accessDeniedPage("/app/error")
				.and()
				.formLogin()
				.defaultSuccessUrl("/bidList/list")
				.and()
				.oauth2Login()
				.defaultSuccessUrl("/bidList/list");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
				.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery(
						"SELECT username as username, password as password, 1 FROM Users WHERE username =?")
				.authoritiesByUsernameQuery(
						"SELECT username, role FROM Users WHERE username=?");
	}
}
