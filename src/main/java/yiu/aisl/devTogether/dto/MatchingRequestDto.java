package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

public class MatchingRequestDto {



    @Getter
    @Setter
    public static class ApplyDTO {
        private Long toUserId;
        private Long fromUserId;

    }

    @Getter
    @Setter
    public static class ApproveDTO {
        private Long matchingId;
        private Long toUserId;
        private Long fromUserId;
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
        private StatusCategory status;
        private Long toUserId;
        private Long fromUserId;

    }

    @Getter
    @Setter
    public static class ConfirmDTO {
        private Long matchingId;
        private StatusCategory status;
        private Long toUserId;
        private Long fromUserId;
    }

    @Getter
    @Setter
    public static class EndDTO {
        private Long matchingId;
        private StatusCategory status;
    }
}
