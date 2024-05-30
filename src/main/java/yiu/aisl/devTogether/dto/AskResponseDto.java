package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AskResponseDto {
    private Long askId;
    private Long userId;
    private String title;
    private String contents;
    private StatusCategory status;
    private String answer;
   // private Boolean files;
    private AskCategory askCategory;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;


    public static AskResponseDto GetAskDTO(Ask ask) {
        return new AskResponseDto(

                ask.getAskId(),
                ask.getUser().getId(),
                ask.getTitle(),
                ask.getContents(),
                ask.getStatus(),
                ask.getAnswer(),
               /* ask.getFiles(),*/
                ask.getAskCategory(),
                ask.getCreatedAt(),
                ask.getUpdatedAt()
        );
    }

}
