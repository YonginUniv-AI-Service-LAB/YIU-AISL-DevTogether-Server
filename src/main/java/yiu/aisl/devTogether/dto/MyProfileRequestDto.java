package yiu.aisl.devTogether.dto;

import com.google.firebase.remoteconfig.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileRequestDto {
    private String email;
    private String name;
    private String nickname;
    private Integer role;
    private Integer gender;
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
}
