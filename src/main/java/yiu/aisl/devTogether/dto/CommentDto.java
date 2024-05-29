package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;
import yiu.aisl.devTogether.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String contents;
    private String title;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long boardId;

    public CommentDto(Comment comment) {
        this.boardId = comment.getBoard().getBoardId();
        this.contents = comment.getBoard().getContents();
        this.title = comment.getBoard().getTitle();
        this.createAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.userId = comment.getUser().getId();
    }
}
