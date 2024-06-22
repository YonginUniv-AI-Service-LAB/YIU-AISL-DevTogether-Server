package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileResponseDto {
    private String email;
    private String name;
    private String role;
    private String gender;
    private Integer age;
    private String location1;
    private String location2;
    private String location3;
    private String answer;
    private Integer question;
}
