package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;

public class MessageRequestDto {

    @Getter
    @Setter
    public static class sendDto{
        private String title;
        private String contents;
        private String toUserId;
    }
}
