package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.state.RoleCategory;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
public class LoginRequestDto {
    private String  email;
    private String  pwd;
    private Integer role;
    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role=" + role +
                '}';
    }
}