package com.blogsystem.security.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class JwtUser implements UserDetails {
    private UUID userId;
    private String email;
    private String password;
    private String fullName;
    private String userName;
    private int status;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(UUID userId, String email, String fullName, String userName, int status, Collection<? extends GrantedAuthority> authorities) {
        this.userId=userId;
        this.email = email;
        this.fullName = fullName;
        this.userName = userName;
        this.status = status;
        this.authorities = authorities;
    }

    public JwtUser(UUID userId, String email, String password, String fullName, String userName, int status, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userName = userName;
        this.status = status;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public UUID getId() {
        return userId;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}
