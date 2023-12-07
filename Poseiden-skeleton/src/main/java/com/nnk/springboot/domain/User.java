package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

/**
 * Objects of this class represent a user of the application<br>
 * This class is mapped to the table users in the database<br>
 * This class is used for authentication through the CustomUserDetails class
 */

@Entity
@Table(name = "users")
public class User {
    /**
     * An Integer corresponding to the id of the User in the database
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * A String corresponding to the username of the User<br>
     * The corresponding column in the database is a varchar(125)
     */
    @Column(length = 125, unique = true)
    @Size(max = 125, message = "Username should be less than 126 characters")
    @NotBlank(message = "Username is mandatory")
    private String username;

    /**
     * A String corresponding to the hashed password of the User<br>
     * The corresponding column in the database is a varchar(125)
     */
    @Column(length = 125)
    @Size(max = 125, message = "Password should be less than 126 characters")
    @NotBlank(message = "Password is mandatory")
    private String password;

    /**
     * A String corresponding to the fullname of the User<br>
     * The corresponding column in the database is a varchar(125)
     */
    @Column(length = 125)
    @Size(max = 125, message = "FullName should be less than 126 characters")
    private String fullname;

    /**
     * A String corresponding to the role of the User<br>
     * The corresponding column in the database is a varchar(125)
     */
    @Column(length = 125)
    @Size(max = 125, message = "Role should be less than 126 characters")
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

    /**
     * This method validates that the user's password :<br>
     * -is at least 8 characters long<br>
     * -includes a number<br>
     * -includes a non-word character<br>
     * -includes an uppercase character<br>
     * -includes a lowercase character<br>
     * <b>This method is intended to be used BEFORE the password is hashed</b><br>
     * Once the user has been saved to the database and its password has been hashed,
     * the result of this method is not correlated to the strength of the password anymore
     * @return true if the password meets the requirements
     */
    public Boolean validatePassword() {
        Boolean length = password.matches("\\S{8,}");
        Boolean digit = password.matches(".*\\d.*");
        Boolean nonword = password.matches(".*\\W.*");
        Boolean uppercase = password.matches(".*[A-Z].*");
        Boolean lowercase = password.matches(".*[a-z].*");
        return length && digit && nonword && uppercase && lowercase;
    }

    /**
     * This method translates the User object to a String object formatted like the output
     * of a html form<br>
     * It is used for integration tests
     * @return a String containing the username, password, fullname, and role of the User
     */
    @Override
    public String toString() {
        return "&username=" + this.username +
                "&password=" + this.password +
                "&fullname=" + this.fullname +
                "&role=" + this.role;
    }
}
