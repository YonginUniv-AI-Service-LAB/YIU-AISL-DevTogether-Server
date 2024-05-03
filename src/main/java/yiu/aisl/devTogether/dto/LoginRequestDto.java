package yiu.aisl.devTogether.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
public class LoginRequestDto {
    private String  email;
    private String  pwd;
    private Integer role;
}