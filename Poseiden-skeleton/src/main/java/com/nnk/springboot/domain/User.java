package com.nnk.springboot.domain;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userid;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least 1 uppercase and 1 digit")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(() -> "USER");
        if(role.equals("ADMIN")) {
            authorities.add(() -> "ADMIN");
        }
        return authorities;
    }
}
