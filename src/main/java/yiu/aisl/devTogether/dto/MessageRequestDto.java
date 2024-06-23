package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;
import yiu.aisl.devTogether.domain.UserProfile;

public class MessageRequestDto {

    @Getter
    @Setter
    public static class sendDto{
        private String title;
        private String contents;
        private Long toUserId;
    }
}
