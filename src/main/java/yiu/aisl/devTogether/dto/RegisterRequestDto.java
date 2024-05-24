package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.state.QuestionCategory;


import java.io.File;


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
    private String birth;
    private Integer question;
    private String answer;






}