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
    private Board board;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private User userId;
    private Long boardId;

    public static CommentDto getCommentDto(Comment comment){
        return new CommentDto(
                comment.getBoard(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser(),
                comment.getBoard().getBoardId()
        );
    }

    public CommentDto(Comment comment) {
        this.boardId = comment.getBoard().getBoardId();
        this.contents = comment.getContents();
        this.createAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.userId = comment.getUser();
    }
}
