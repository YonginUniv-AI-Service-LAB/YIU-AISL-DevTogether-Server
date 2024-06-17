package yiu.aisl.devTogether.config;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yiu.aisl.devTogether.domain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Setter
//UserDetails란?
// Spring Security에서 사용자의 정보를 담는 인터페이스

public class CustomUserDetails implements UserDetails {
    private final User user;

    private int role;

    public CustomUserDetails(User user, int role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 사용자의 역할(role)에 따라 권한을 설정합니다.
        if (role == 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (role == 1) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MENTOR"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_MENTEE"));
        }
        return authorities;
    }



    // CustomUserDetails 클래스는 User 클래스를 내부에 포함하고 있음  따라서 getEmail 가능
    public CustomUserDetails(User user) {
        this.user = user;
    }
    public final User getUser() {
        return user;
    }


    public String getEmail() {
        return user.getEmail();
    }                //추가함

    @Override
    public String getPassword() {      //계정의 비밀번호 리턴
        return user.getPwd();
    }

    @Override
    public String getUsername() { //계정의 고유한 값 리턴
        return user.getName();
    }





    @Override
    public boolean isAccountNonExpired() { //계정 만료 여부 리턴
        return true; // 만료 안됨
    }

    @Override
    public boolean isAccountNonLocked() {//계정  잠김 여부 리턴
        return true; //잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부 리턴
        return true; //만료 안됨
    }

    @Override
    public boolean isEnabled() {//계정 활성화 여부 리턴
        return true; //활성화 안됨
    }
}