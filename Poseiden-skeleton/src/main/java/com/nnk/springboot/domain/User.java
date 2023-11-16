package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(length = 125)
    @Size(max = 125, message = "Username should be less than 126 characters")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Column(length = 125)
    @Size(max = 125, message = "Password should be less than 126 characters")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column(length = 125)
    @Size(max = 125, message = "FullName should be less than 126 characters")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @Column(length = 125)
    @Size(max = 125, message = "Role should be less than 126 characters")
    @NotBlank(message = "Role is mandatory")
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

    public Boolean validatePassword() {
        Boolean length = password.matches("\\S{8,}");
        Boolean digit = password.matches(".*\\d.*");
        Boolean nonword = password.matches(".*\\W.*");
        Boolean uppercase = password.matches(".*[A-Z].*");
        Boolean lowercase = password.matches(".*[a-z].*");
        return length && digit && nonword && uppercase && lowercase;
    }

    @Override
    public String toString() {
        return "&username=" + this.username +
                "&password=" + this.password +
                "&fullname=" + this.fullname +
                "&role=" + this.role;
    }
}
