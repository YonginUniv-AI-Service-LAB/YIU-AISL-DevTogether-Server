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
        private Integer subject;
        private String contents;
        private Integer tutoringFee;
    }
    @Getter
    @Setter
    public static class MenteeApplyDTO {
        private UserProfile mentee;
        private Integer subject;
        private String contents;
        private Integer tutoringFee;
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
