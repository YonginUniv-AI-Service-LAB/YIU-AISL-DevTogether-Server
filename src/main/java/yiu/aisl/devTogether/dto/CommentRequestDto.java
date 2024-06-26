package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String contents;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long boardId;

    public CommentRequestDto(Comment comment) {
        this.boardId = comment.getBoard().getBoardId();
        this.contents = comment.getBoard().getContents();
        this.title = comment.getBoard().getTitle();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.userId = comment.getUserProfile().getUser().getId();
    }
}
