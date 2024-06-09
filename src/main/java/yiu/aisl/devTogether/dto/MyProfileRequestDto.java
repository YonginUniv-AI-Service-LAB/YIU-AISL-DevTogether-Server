package yiu.aisl.devTogether.dto;

import com.google.firebase.remoteconfig.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.state.QuestionCategory;

import java.util.List;

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
    private QuestionCategory question;
    private String answer;

}
