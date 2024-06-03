package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

public class MatchingRequestDto {

    @Getter
    @Setter
    public static class applyDTO {
        private UserProfile mentee;
        private UserProfile mentor;


    }

    @Getter
    @Setter
    public static class MentorApplyDTO {
        private UserProfile mentee;


    }
    @Getter
    @Setter
    public static class MenteeApplyDTO {
        private UserProfile mentor;
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
    public class ScrapDto {
        private Long scrapId;
    }
}
