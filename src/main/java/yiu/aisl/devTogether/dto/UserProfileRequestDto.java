package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDto {
    private String introduction;
    private String pr;
    private String link;
    private String contents;
    private String schedule;
    private String method;
    private Integer fee;
    private LocalDateTime updatedAt;
}
