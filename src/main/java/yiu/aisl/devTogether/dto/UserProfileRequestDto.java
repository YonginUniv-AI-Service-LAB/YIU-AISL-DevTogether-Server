package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDto {
    private String introduction;
    private String pr;
    private String portfolio;
    private String contents;
    private String schedule;
    private String method;
    private String  subject1;
    private String  subject2;
    private String  subject3;
    private String  subject4;
    private String  subject5;
    private Integer fee;
    private MultipartFile img;
    private LocalDateTime updatedAt;
}
