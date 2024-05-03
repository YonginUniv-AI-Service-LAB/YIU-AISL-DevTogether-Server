package yiu.aisl.devTogether.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가

public class RegisterDto {


    private String  email;
    private String  pwd;
    private String  name;
    private String  nickname;
    private Integer role;
    private Integer gender;
    private String  img;
    private Integer age;
    private String  phone;
    private String  location1;
    private String  location2;
    private String  location3;








}