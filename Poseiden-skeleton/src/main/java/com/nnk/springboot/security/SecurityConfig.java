package com.nnk.springboot.security;

import com.nnk.springboot.service.IUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private IUserDetailService userDetailService;

    public SecurityConfig(IUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers(
                        "/registration/**",
                        "/js/**",
                        "/css/**",
                        "/img/**").permitAll()
                .antMatchers("/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().defaultSuccessUrl("/loginsuccess", true)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");


    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers("/css/**", "/static/**", "/img/**")
//                .antMatchers("/h2-console/**");
//
//    }


//    private UserPrincipal userPrincipal;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
////        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//
//            auth.inMemoryAuthentication()
//                    .withUser("springUser").password(passwordEncoder().encode("spring123")).roles("USER")
//                    .and()
//                    .withUser("springAdmin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER");
//
////            auth.jdbcAuthentication().withUser(userPrincipal.getUsername()).password(passwordEncoder().encode(userPrincipal.getPassword())).roles("USER")
////                    .and()
////                    .withUser(userPrincipal.getUsername()).password(passwordEncoder().encode(userPrincipal.getPassword())).roles("ADMIN", "USER");
//
//
//        }

//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http.csrf()
//                    .disable()
//                    .authorizeRequests()
//                    .antMatchers("/home", "/", "/403").permitAll()
//                    .antMatchers("/user/**").hasAuthority("ADMIN")
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .and()
//                    .oauth2Login();
//        }


}
