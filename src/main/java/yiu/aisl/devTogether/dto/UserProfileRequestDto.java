package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.state.SubjectCategory;

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
    private Integer  subject1;
    private Integer  subject2;
    private Integer  subject3;
    private Integer  subject4;
    private Integer  subject5;
    private Integer fee;
    private MultipartFile img;
    private LocalDateTime updatedAt;
}
