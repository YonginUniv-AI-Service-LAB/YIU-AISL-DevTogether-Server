package yiu.aisl.devTogether.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.User;
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
    private StatusCategory status;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static MatchingRequestDto GetMatchingDTO(Matching matching) {
        return new MatchingRequestDto(
/*
                matching.getMatchingId(),
                matching.getUser.getId(),
                matching.getUser.getId(),
                matching.getStatus(),
                matching.getCreatedAt(),
                matching.getUpdatedAt()*/
        );
    }
}
