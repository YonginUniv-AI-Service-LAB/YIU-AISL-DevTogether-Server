package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.io.File;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
    private String  email;
    private String  pwd;
    private String  name;
    private String  nickname;
    private RoleCategory role;
    private GenderCategory gender;
    private Boolean  img;
    private Integer age;
    private String method;
    private Integer  fee;
    private String  location1;
    private String  location2;
    private String  location3;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private File file;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static RegisterResponseDto GetRegisterDTO(User user) {
        return new RegisterResponseDto(
                user.getEmail(),
                user.getPwd(),
                user.getName(),
                user.getNickname(),
                user.getRole(),
                user.getGender(),
                user.getImg(),
                user.getAge(),
                user.getMethod(),
                user.getFee(),
                user.getLocation1(),
                user.getLocation2(),
                user.getLocation3(),
                user.getSubject1(),
                user.getSubject2(),
                user.getSubject3(),
                user.getSubject4(),
                user.getSubject5(),
                user.getFile(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
