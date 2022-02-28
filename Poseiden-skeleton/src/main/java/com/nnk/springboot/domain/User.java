package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Length(max = 125, message = "must have 125 caracters max")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Length(max = 125, message = "must have 125 caracters max")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Length(max = 125, message = "must have 125 caracters max")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    @Length(max = 125, message = "must have 125 caracters max")
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
