package yiu.aisl.devTogether.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yiu.aisl.devTogether.domain.User;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final User user;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public CustomUserDetails(User user) {
        this.user = user;
    }
    public final User getUser() {
        return user;
    }


    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }


    @Override
    public boolean isAccountNonExpired() { //계정 만료되어있는지
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//계정 잠겨있는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//계정 자격 증명이 만료되었는지
        return true;
    }

    @Override
    public boolean isEnabled() {//계정 활성화되어있는지
        return true;
    }
}