package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.*;

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
    private UserProfileResponseDto2 userProfileId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Boolean files;
    private List<FilesResponseDto> filesList;
    private Integer likeCount;
    private List<CommentDto> comments;
    private Integer countComment;

    public static BoardDto getboardDto(Board board) {
        UserProfile userProfile = board.getUserProfile();
        UserProfileResponseDto2 userProfileDto = new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getFiles(),
                userProfile.getFilesdata()
        );
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .contents(board.getContents())
                .userProfileId(userProfileDto)
                .createAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .files(board.getFiles())
                .filesList(board.getFilesList())
                .likeCount(board.getLikes().size())
                .comments(board.getComments().stream()
                        .map(CommentDto::new)
                        .collect(Collectors.toList()))
                .countComment(board.getComments().size())
                .build();
    }

}
