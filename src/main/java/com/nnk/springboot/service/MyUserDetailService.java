package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Users;

@Service("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(MyUserDetailService.class);

    @Autowired
    private LoggerApi loggerApi;

    @Autowired
    private UsersService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.trim().isEmpty()) {

            String messageLoggerInfo = loggerApi.loggerStrings("User Name is empty.", "", "");
            LOGGER.warn(messageLoggerInfo);

            throw new UsernameNotFoundException("username is empty");
        }

        Users user = userService.getUser(username);

        if (user == null) {

            String messageLoggerInfo = loggerApi.loggerStrings("User name : ", username, " not found.");
            LOGGER.warn(messageLoggerInfo);

            throw new UsernameNotFoundException("User " + username + " not found");

        }

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi.loggerDebug("User Name is : " + username);
            LOGGER.debug(messageLoggerDebug);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Users users) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = users.getRole();
        authorities.add(new SimpleGrantedAuthority(role));

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi
                    .loggerDebug(
                            "The user name : " + users.getUsername() + " " + users.getFullname()
                                    + " has the authoritie "
                                    + role);
            LOGGER.debug(messageLoggerDebug);
        }

        return authorities;
    }
}
