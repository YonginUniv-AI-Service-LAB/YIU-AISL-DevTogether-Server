package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;
import java.util.List;

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
    private Boolean files;
    private List<FilesResponseDto> filesList;
    private AskCategory askCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static AskResponseDto GetAskDTO(Ask ask) {
        return new AskResponseDto(

                ask.getAskId(),
                ask.getUser().getId(),
                ask.getTitle(),
                ask.getContents(),
                ask.getStatus(),
                ask.getAnswer(),
                ask.getFiles(),
                ask.getFilesList(),
                ask.getAskCategory(),
                ask.getCreatedAt(),
                ask.getUpdatedAt()
        );
    }

}
