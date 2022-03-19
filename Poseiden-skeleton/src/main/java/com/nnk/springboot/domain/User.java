package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Column(name = "username")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "must contain at least 8 characters with a lowercase, an uppercase, a number and a special character (#?!@$%^&*-)")
    @Length(max = 125, message = "must have 125 caracters max")
    @Column(name = "password")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Length(max = 125, message = "must have 125 caracters max")
    @Column(name = "fullname")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    @Length(max = 125, message = "must have 125 caracters max")
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

	public User(
			@NotBlank(message = "Username is mandatory") @Length(max = 125, message = "must have 125 caracters max") String username,
			@NotBlank(message = "Password is mandatory") @Length(max = 125, message = "must have 125 caracters max") String password,
			@NotBlank(message = "FullName is mandatory") @Length(max = 125, message = "must have 125 caracters max") String fullname,
			@NotBlank(message = "Role is mandatory") @Length(max = 125, message = "must have 125 caracters max") String role) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", fullname=" + fullname + ", role=" + role
				+ "]";
	}
    
}
