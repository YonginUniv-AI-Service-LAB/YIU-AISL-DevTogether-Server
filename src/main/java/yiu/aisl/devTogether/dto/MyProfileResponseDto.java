package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileResponseDto {
    private String email;
    private String name;
    private String nickname;
    private String role;
    private String gender;
    private Integer age;
    private String location1;
    private String location2;
    private String location3;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private String method;
    private Integer fee;
    private Boolean img;
}
