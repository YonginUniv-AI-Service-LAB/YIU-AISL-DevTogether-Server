package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyBoardDto {
    private Long boardId;
    private String title;
    private String contents;
    private Long userId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Boolean files;
    private List<FilesResponseDto> filesList;

    public MyBoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.userId = board.getUserProfile().getUser().getId();
        this.files = board.getFiles();
        this.filesList = board.getFilesList();
    }
}
