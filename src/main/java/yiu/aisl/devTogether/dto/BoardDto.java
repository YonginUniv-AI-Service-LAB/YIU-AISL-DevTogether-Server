package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;
import yiu.aisl.devTogether.domain.Files;
import yiu.aisl.devTogether.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long boardId;
    private String title;
    private String contents;
    private Long userProfileId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Boolean files;
    private List<FilesResponseDto> filesList;
    private Integer likeCount;
    private List<CommentDto> comments;

    public static BoardDto getboardDto(Board board) {
        return new BoardDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContents(),
                board.getUserProfile().getUser().getId(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getFiles(),
                board.getFilesList(),
                board.getLikes().size(),
                board.getComments().stream()
                        .map(CommentDto::new)
                        .collect(Collectors.toList())
        );
    }

//        public BoardDto(Board board) {
//            this.boardId = board.getBoardId();
//            this.contents = board.getContents();
//            this.createAt = board.getCreatedAt();
//            this.updatedAt = board.getUpdatedAt();
//            this.userId = board.getUser().getId();
//            this.files = board.getFiles();
//            this.comments = board.getComments();
//            this.filesList = board.getFilesList();
//        }
}
