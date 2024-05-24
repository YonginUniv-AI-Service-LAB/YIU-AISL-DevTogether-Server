package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yiu.aisl.devTogether.domain.Message;
import yiu.aisl.devTogether.domain.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private Long messageId;
    private String title;
    private String contents;
    private User fromUserId;
    private User toUserid;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MessageResponseDto getMessageDto(Message message){
        return new MessageResponseDto(
                message.getMessageId(),
                message.getTitle(),
                message.getContents(),
                message.getFromUserId(),
                message.getToUserid(),
                message.getStatus(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}
