package com.graduatebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@Getter
@ToString
@Log4j2
public class UserPrincipal implements UserDetails {

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * create UserPrincipal by User
     *
     * @param user
     * @return
     */

    public static UserPrincipal create(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        try {
//            authorities = user.getRoles().stream()
//                    .map(role -> {
//                        String roleTitle = role.getRoleTitle();
//                        if (roleTitle != null) {
//                            try {
//                                return new SimpleGrantedAuthority("ROLE_" + roleTitle);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        return null;
//                    })
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//           log.info(e.getMessage());
//        }
//        return new UserPrincipal(user.getUserMailAddress(), user.getUserPassword(), authorities);
        return new UserPrincipal(user.getUserMailAddress(), user.getUserPassword(), getAuthorities(user.getRoles()));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(
            Set<Role> roles) {
        return getGrantedAuthorities(getPermission(roles));
    }

    private static List<String> getPermission(Set<Role> roles) {

        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            permissions.add("ROLE_" + role.getRoleTitle());
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add("ROLE_" + item.getPermissionTitle());
        }
        return permissions;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
