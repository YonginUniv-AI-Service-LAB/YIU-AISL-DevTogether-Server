package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

public class MatchingRequestDto {



    @Getter
    @Setter
    public static class MentorApplyDTO {
        private Integer matchingCategory;
        private Long mentee;


    }
    @Getter
    @Setter
    public static class MenteeApplyDTO {
        private Integer matchingCategory;
        private Long mentor;

    }

    @Getter
    @Setter
    public static class ApproveDTO {
        private Long matchingId;
        private Long mentor;
        private Long mentee;
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
