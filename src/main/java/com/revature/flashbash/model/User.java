package com.revature.flashbash.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity(name = "users")
public class User {

    @Id
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;
    @Column
    private String lastName;

    @Enumerated
    private UserType userType;
}
