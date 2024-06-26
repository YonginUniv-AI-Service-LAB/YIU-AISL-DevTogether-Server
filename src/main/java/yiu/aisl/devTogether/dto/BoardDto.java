package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.BoardCategory;
import yiu.aisl.devTogether.service.FilesService;

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
    private BoardCategory category;
    private String title;
    private String contents;
    private UserProfileResponseDto2 userProfileId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean files;
    private List<FilesResponseDto> filesList;
    private Integer likeCount;
    private List<CommentDto> comments;
    private Integer countComment;
    private List<Long> likePeople;
    private List<Long> scrapPeople;
    public static BoardDto getboardDto(Board board) {
        UserProfile userProfile = board.getUserProfile();
        UserProfileResponseDto2 userProfileDto = new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getIntroduction(),
                userProfile.getFiles(),
                userProfile.getFilesdata()
        );

        return BoardDto.builder()
                .boardId(board.getBoardId())
                .category(board.getCategory())
                .title(board.getTitle())
                .contents(board.getContents())
                .userProfileId(userProfileDto)
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .files(board.getFiles())
                .filesList(board.getFilesList())
                .likeCount(board.getLikes().size())
                .comments(board.getComments().stream()
                        .map(CommentDto::new)
                        .collect(Collectors.toList()))
                .countComment(board.getComments().size())
                .likePeople(board.getLikes().stream()
                        .map(likes -> likes.getUserid().getUserProfileId())
                        .collect(Collectors.toList()))
                .scrapPeople(board.getBoardScraps().stream()
                        .map(boardScrap -> boardScrap.getUser().getUserProfileId())
                        .collect(Collectors.toList()))
                .build();
    }

}
