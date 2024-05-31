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
    private Long userId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Boolean files;
    private List<FilesResponseDto> filesList;
    private List<Comment> comments;

    public static BoardDto getboardDto(Board board) {
        return new BoardDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContents(),
                board.getUser().getId(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getFiles(),
                board.getFilesList(),
                board.getComments()
        );
    }

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.userId = board.getUser().getId();
        this.files = board.getFiles();
        this.comments = board.getComments();
        this.filesList = board.getFilesList();
    }
}
