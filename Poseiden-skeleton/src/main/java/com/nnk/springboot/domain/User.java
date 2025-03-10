package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true)
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "The password must contain at least 8 characters, a capital letter, a number and a symbol")
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(name = "role")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_USER", message = "Role must be ROLE_ADMIN or ROLE_USER")
    private String role;
}
