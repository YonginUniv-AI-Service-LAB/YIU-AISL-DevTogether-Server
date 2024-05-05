package yiu.aisl.devTogether.config;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yiu.aisl.devTogether.domain.User;

import java.util.Collection;
import java.util.Collections;

@Setter
//UserDetails란?
// Spring Security에서 사용자의 정보를 담는 인터페이스

public class CustomUserDetails implements UserDetails {
    private final User user;

    private String role;

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {   //계정의 권한 목록 리턴
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
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
    }

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