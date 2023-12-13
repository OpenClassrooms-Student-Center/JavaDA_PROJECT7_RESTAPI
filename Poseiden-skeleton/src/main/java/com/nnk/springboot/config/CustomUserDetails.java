package com.nnk.springboot.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.nnk.springboot.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is a custom implementation of the UserDetails class.<br>
 * It maps a User object to a UserDetails object,
 * which can then be interpreted by SpringSecurity
 * for authentication and authorization purposes.
 */
public class CustomUserDetails implements UserDetails {
    /**
     * the domain User object that is mapped to a SpringSecurity UserDetails objects
     */
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * This method returns the authorities of a user, based on its role attribute
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(!user.getRole().isBlank()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return authorities;
        }
        return null;
    }

    @Override public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * All accounts are considered non expired
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * All accounts are considered non locked
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * All credentials are considered non expired
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * All accounts are considered enabled
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
