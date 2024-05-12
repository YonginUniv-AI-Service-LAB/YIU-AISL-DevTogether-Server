package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AskResponseDto {
    private Long askId;
    private User userId;
    private String title;
    private String contents;
    private StatusCategory status;
    private String answer;
    private String file;
    private AskCategory askCategory;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;


    public static AskResponseDto GetAskDTO(Ask ask) {
        return new AskResponseDto(

                ask.getAskId(),
                ask.getUserId(),
                ask.getTitle(),
                ask.getContents(),
                ask.getStatus(),
                ask.getAnswer(),
                ask.getFile(),
                ask.getAskCategory(),
                ask.getCreatedAt(),
                ask.getUpdatedAt()
        );
    }

}