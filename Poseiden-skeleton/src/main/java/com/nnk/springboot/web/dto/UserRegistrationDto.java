package com.nnk.springboot.web.dto;
/**
 * user data transform object
 */
public class UserRegistrationDto {

    private String username;
    private String fullname;
    private String password;

    private String role;


    public UserRegistrationDto() {

    }

    public UserRegistrationDto(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
