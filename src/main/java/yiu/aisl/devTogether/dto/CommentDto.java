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
    private Board boardId;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private User userId;
    private Long commentlike;

    public static CommentDto getCommentDto(Comment comment){
        return new CommentDto(
                comment.getBoardId(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUserId(),
                comment.getLike()
        );
    }
}
