package yiu.aisl.devTogether.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.MatchingCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingResponseDto {

    private Long matchingId;
    private Long mentor;
    private Long mentee;

    private StatusCategory status;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endedAt;

    public static MatchingResponseDto GetMatchingDTO(Matching matching) {
        return new MatchingResponseDto(

                matching.getMatchingId(),
                matching.getMentor().getUser().getId(),
                matching.getMentee().getUser().getId(),
                matching.getStatus(),
                matching.getCreatedAt(),
                matching.getUpdatedAt(),
                matching.getEndedAt()
        );
    }

    public MatchingResponseDto(Matching matching) {
        this.matchingId = matching.getMatchingId();
        this.mentor = matching.getMentor().getUser().getId();
        this.mentee = matching.getMentee().getUser().getId();
        this.status = matching.getStatus();
        this.createAt = matching.getCreatedAt();
        this.updatedAt = matching.getUpdatedAt();
        this.endedAt = matching.getEndedAt();
    }
}
