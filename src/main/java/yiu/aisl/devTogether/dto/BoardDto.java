package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;
import yiu.aisl.devTogether.domain.Files;
import yiu.aisl.devTogether.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long boardId;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private User userId;
    private Boolean files;
    private List<Comment> comments;
    private List<Files> filesList;

    public static BoardDto getboardDto(Board board) {
        return new BoardDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getUser(),
                board.getFiles(),
                board.getComments(),
                board.getFilesList()
        );
    }
}
