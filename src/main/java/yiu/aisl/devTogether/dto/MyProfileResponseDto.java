package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.state.SubjectCategory;

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
    private SubjectCategory subject1;
    private SubjectCategory subject2;
    private SubjectCategory subject3;
    private SubjectCategory subject4;
    private SubjectCategory subject5;
    private String method;
    private Integer fee;
    private Boolean img;
}
