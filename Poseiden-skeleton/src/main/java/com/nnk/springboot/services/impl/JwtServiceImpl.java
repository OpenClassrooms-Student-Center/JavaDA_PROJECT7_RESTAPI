package com.nnk.springboot.services.impl;

import com.nnk.springboot.config.security.JwtTokenUtil;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * The type Jwt service.
 */
@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtTokenUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    /**
     * authenticate
     * need for authenticate api user
     * @param user
     * @return Bearer token
     * @throws Exception
     */
    public String authenticate(User user) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch(BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtUtil.generateToken(userDetails);
    }
}
