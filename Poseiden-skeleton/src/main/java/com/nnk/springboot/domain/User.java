package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    @Size(max = 125)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "fullname")
    @Size(max = 125)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(name = "role")
    @Size(max = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User() {
    }

    public User( String username,String fullname, String role) {

    }
}