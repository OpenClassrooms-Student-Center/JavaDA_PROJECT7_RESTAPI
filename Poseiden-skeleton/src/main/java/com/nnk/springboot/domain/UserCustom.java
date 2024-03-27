package com.nnk.springboot.domain;

import com.nnk.springboot.configuration.CustomValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserCustom {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username")
    private String username;
    @CustomValidation
    @Column(name = "password")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    @Column(name = "role")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
