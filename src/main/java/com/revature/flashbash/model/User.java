package com.revature.flashbash.model;

import javax.persistence.*;

@Entity(name = "users")
public class User { // the default entity name would have been user

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated
    private UserType userType;







    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public User setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
