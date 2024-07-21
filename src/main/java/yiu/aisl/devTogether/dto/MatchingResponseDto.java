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
    private Integer checkReview;
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
    private String method;
    private String schedule;
    private String location1;
    private String location2;
    private String location3;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endedAt;

    public static MatchingResponseDto GetMatchingDTO(Matching matching) {
        return new MatchingResponseDto(
                matching.getMatchingId(),
                matching.getCheckReview(),
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
                matching.getMethod(),
                matching.getSchedule(),
                matching.getLocation1(),
                matching.getLocation2(),
                matching.getLocation3(),
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
        this.tutoringFee = matching.getTutoringFee();
        this.contents = matching.getContents();
        this.method = matching.getMethod();
        this.schedule = matching.getSchedule();
        this.location1 = matching.getLocation1();
        this.location2 = matching.getLocation2();
        this.location3 = matching.getLocation3();
        this.createAt = matching.getCreatedAt();
        this.updatedAt = matching.getUpdatedAt();
        this.endedAt = matching.getEndedAt();
    }
}
