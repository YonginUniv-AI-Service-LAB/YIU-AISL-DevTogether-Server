package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yiu.aisl.devTogether.domain.Message;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private Long messageId;
    private String title;
    private String contents;
    private Long fromUserId;
    private String fromUserNickName;
    private Long toUserid;;
    private String toUserNickName;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MessageResponseDto getMessageDto(Message message){
        return MessageResponseDto.builder()
                .messageId(message.getMessageId())
                .title(message.getTitle())
                .contents(message.getContents())
                .fromUserId(message.getFromUserId().getUserProfileId())
                .fromUserNickName(message.getFromUserId().getNickname())
                .toUserid(message.getToUserId().getUserProfileId())
                .toUserNickName(message.getToUserId().getNickname())
                .status(message.getStatus())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build();
    }
}
