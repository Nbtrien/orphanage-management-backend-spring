package com.graduatebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class AccountPrincipal implements UserDetails {

    private String email;

    @JsonIgnore
    private String password;

    private String role = "USER";

    public AccountPrincipal(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * create UserPrincipal by User
     *
     * @param user
     * @return
     */

    public static AccountPrincipal create(Account account) {
        if (account == null) {
            throw new UsernameNotFoundException("Account not found");
        }
        return new AccountPrincipal(account.getAccountMailAddress(), account.getAccountPassword());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}