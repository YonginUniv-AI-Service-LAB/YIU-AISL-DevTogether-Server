package yiu.aisl.devTogether.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Matching;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingResponseDto {

    private Long matchingId;
    private Long mentor;
    private Long mentee;
    private String status;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private Integer tutoringFee;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endedAt;

    public static MatchingResponseDto GetMatchingDTO(Matching matching) {
        return new MatchingResponseDto(

                matching.getMatchingId(),
                matching.getMentor().getUserProfileId(),
                matching.getMentee().getUserProfileId(),
                matching.getStatus(),
                matching.getSubject1(),
                matching.getSubject2(),
                matching.getSubject3(),
                matching.getSubject4(),
                matching.getSubject5(),
                matching.getTutoringFee(),
                matching.getContents(),
                matching.getCreatedAt(),
                matching.getUpdatedAt(),
                matching.getEndedAt()
        );
    }

    public MatchingResponseDto(Matching matching) {
        this.matchingId = matching.getMatchingId();
        this.mentor = matching.getMentor().getUserProfileId();
        this.mentee = matching.getMentee().getUserProfileId();
        this.status = matching.getStatus();
        this.subject1 = matching.getSubject1();
        this.subject2 = matching.getSubject2();
        this.subject3 = matching.getSubject3();
        this.subject4 = matching.getSubject4();
        this.subject5 = matching.getSubject5();
        this.contents = matching.getContents();
        this.tutoringFee = matching.getTutoringFee();
        this.createAt = matching.getCreatedAt();
        this.updatedAt = matching.getUpdatedAt();
        this.endedAt = matching.getEndedAt();
    }
}
