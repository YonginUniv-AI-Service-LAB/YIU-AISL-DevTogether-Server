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
    private Long board;
    private String contents;
    private Long userId;
    private Long boardId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static CommentDto getCommentDto(Comment comment){
        return new CommentDto(
                comment.getBoard().getBoardId(),
                comment.getContents(),
                comment.getUserProfile().getUser().getId(),
                comment.getBoard().getBoardId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public CommentDto(Comment comment) {
        this.boardId = comment.getBoard().getBoardId();
        this.contents = comment.getContents();
        this.createAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.userId = comment.getUserProfile().getUser().getId();
    }
}
