package com.nnk.springboot.config;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Security configuration for the application.
 */
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Constructor for SecurityConfig that initializes the user service.
     *
     * @param userRepository the user repository
     */
    public SecurityConfig(UserRepository userRepository) {
        this.userDetailsService = new CustomUserDetailsService(userRepository);
    }

    /**
     * Defines the password encoder used to secure user passwords.
     *
     * @return a BCrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the HTTP security configuration for the application.
     *
     * @param http the HttpSecurity object used to configure security
     * @return the configured SecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/login", "/error").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .headers(headers -> headers
                        .xssProtection(xss -> xss
                                .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        )
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self'")
                        )
                );
        return http.build();
    }

    /**
     * Defines a custom authentication success handler that redirects users based on their role.
     *
     * @return an authentication success handler
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            System.out.println("Authority received in handler: " + role);
            if (role.equals("ROLE_ADMIN")) {
                System.out.println("Redirecting to admin home");
                response.sendRedirect("/admin/home");
            } else {
                System.out.println("Redirecting to bid list");
                response.sendRedirect("/bidList/list");
            }
        };
    }
}
