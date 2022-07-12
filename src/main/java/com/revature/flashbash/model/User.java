package com.revature.flashbash.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Entity(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User implements UserDetails { // the default entity name would have been user

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @CreationTimestamp
    private Timestamp registrationTime;

    private boolean isActive = true;


    @Enumerated
    private Authority authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public enum Authority implements GrantedAuthority {
        USER("USER"),
        ADMIN("ADMIN");

        private final String value;

        Authority(String authority){
            this.value = authority;
        }

        @Override
        public String getAuthority() {
            return value;
        }
    }
}
