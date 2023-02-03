package com.nnk.springboot.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Column
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @Column
    @NotBlank(message = "Role is mandatory")
    private String role;

}
