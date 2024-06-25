package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

public class MatchingRequestDto {
    @Getter
    @Setter
    public static class MentorApplyDTO {
        private UserProfile mentor;
        private String subject1;
        private String subject2;
        private String subject3;
        private String subject4;
        private String subject5;
        private String contents;
        private Integer tutoringFee;
        private String method;
        private String schedule;
        private String location1;
        private String location2;
        private String location3;
    }
    @Getter
    @Setter
    public static class MenteeApplyDTO {
        private UserProfile mentee;
        private String subject1;
        private String subject2;
        private String subject3;
        private String subject4;
        private String subject5;
        private String contents;
        private Integer tutoringFee;
        private String method;
        private String schedule;
        private String location1;
        private String location2;
        private String location3;
    }
    @Getter
    @Setter
    public static class ApproveDTO {
        private Long matchingId;
    }
    @Getter
    @Setter
    public static class DeleteDTO {
        private Long matchingId;
    }
    @Getter
    @Setter
    public static class RefusalDTO {
        private Long matchingId;
    }
    @Getter
    @Setter
    public static class ConfirmDTO {
        private Long matchingId;
    }
    @Getter
    @Setter
    public static class EndDTO {
        private Long matchingId;
    }
    @Getter
    @Setter
    public static class ScrapDto {
        private Long scrapId;
    }
}
