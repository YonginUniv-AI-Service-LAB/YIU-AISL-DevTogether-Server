package yiu.aisl.devTogether.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 추가

public class RegisterDto {


    private String email;
    private String pwd;
    private String name;
    private Integer role;
    private Integer gender;
    private Integer age;
    private String  method;
    private Integer fee;
    private String location1;
    private String location2;
    private String location3;


}
