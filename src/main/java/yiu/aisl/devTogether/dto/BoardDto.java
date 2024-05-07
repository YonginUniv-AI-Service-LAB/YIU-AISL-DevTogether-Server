package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;
import yiu.aisl.devTogether.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private User userId;
    private Integer check;
    private Integer majorId;
    private Long like;
    private List<Comment> comments;

    public static BoardDto getboardDto(Board board){
        return new BoardDto(
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getUserId(),
                board.getCheck(),
                board.getMajorId(),
                board.getLike(),
                board.getComments()
        );
    }
}
