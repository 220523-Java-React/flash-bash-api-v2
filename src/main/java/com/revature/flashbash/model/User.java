package com.revature.flashbash.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
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

    public enum UserType {
        USER, ADMIN
    }
}
