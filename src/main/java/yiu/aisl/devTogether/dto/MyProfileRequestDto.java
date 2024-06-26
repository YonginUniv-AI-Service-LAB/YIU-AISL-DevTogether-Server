package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileRequestDto {
    private String email;
    private String name;

    private Integer role;
    private Integer gender;
    private Integer age;
    private String location1;
    private String location2;
    private String location3;
    private Integer question;
    private String answer;

}
