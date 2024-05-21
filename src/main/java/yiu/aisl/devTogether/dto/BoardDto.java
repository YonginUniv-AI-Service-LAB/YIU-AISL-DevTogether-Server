package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;
import yiu.aisl.devTogether.domain.Files;
import yiu.aisl.devTogether.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.userId = board.getUser();
        this.files = board.getFiles();
        this.comments = board.getComments();
        this.filesList = board.getFilesList();
    }
}
