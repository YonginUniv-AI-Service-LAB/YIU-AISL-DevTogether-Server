package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Push;
import yiu.aisl.devTogether.domain.state.PushCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushResponseDto {
    private Long pushId;
    private PushCategory type;
    private Long typeId;
    private String contents;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int checks;

    public PushResponseDto(Push push) {
        this.pushId = push.getId();
        this.type = push.getType();
        this.typeId = push.getTypeId();
        this.contents = push.getContents();
        this.status = push.getStatus();
        this.createdAt = push.getCreatedAt();
        this.updatedAt = push.getUpdatedAt();
        this.checks = push.getChecks();
    }
}
