package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가

public class RegisterRequestDto {

    @Getter
    @Setter
    private String  email;
    private String  pwd;
    private String  name;
    private String  nickname;
    private Integer role;
    private Integer gender;
    private Integer age;
    private String phone;
    private String birth;
    private Integer question;
    private String answer;




}