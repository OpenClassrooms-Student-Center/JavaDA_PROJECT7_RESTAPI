package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Custom implementation of UserDetailsService for loading user details from the database.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    /**
     * Constructor for initializing the user repository.
     *
     * @param userRepository the user repository used to fetch user details
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to be loaded
     * @return the user details associated with the given username
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found: " + username);
                    return new UsernameNotFoundException("User not found");
                });

        logger.info("Found user: " + user.getUsername());
        logger.info("User role from DB: " + user.getRole());

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        logger.info("Granted authority: " + authority.getAuthority());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(authority))
                .build();
    }
}
