package yiu.aisl.devTogether.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingResponseDto {

    private Long matchingId;
    private Long toUserId;
    private Long fromUserId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private StatusCategory status;

    public static MatchingResponseDto GetMatchingDTO(Matching matching) {
        return new MatchingResponseDto(

                matching.getMatchingId(),
                matching.getToUserId().getId(),
                matching.getFromUserId().getId(),
                matching.getCreatedAt(),
                matching.getUpdatedAt(),
                matching.getStatus()
        );
    }
}
