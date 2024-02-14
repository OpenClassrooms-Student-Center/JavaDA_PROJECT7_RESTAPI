/**
 * This module is about impact of the final keyword on performance
 * <p>
 * This module explores  if there are any performance benefits from
 * using the final keyword in our code. This module examines the performance
 * implications of using final on a variable, method, and class level.
 * </p>
 *
 * @since 1.0
 * @author baeldung
 * @version 1.1
 */
package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Cette classe permet de préconfigurer et de personnaliser les fonctions de
 * sécurité
 * 
 * @author Claudiu VILAU
 * 
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

    /**
     * Cette méthode sert à construire la chaîne de filtres selon notre
     * configuration
     * 
     * @param http la classe HttpSecurity est sollicitée pour appliquer la chaîne de
     *             filtres de sécurité aux requêtes HTTP
     * @return la requête HTTP avec les filtres de
     *         sécurité appliqués
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(login -> login
                        .loginPage("/app/login")
                        .defaultSuccessUrl("/app/login/ok", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/app/app-logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true));

        return http.build();
    }

    /**
     * Algorithme de hachage
     * 
     * @return le mot de passe haché
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
