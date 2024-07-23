package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Comment;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long boardId;
    private Long commentId;
    private String contents;
    private UserProfileResponseDto2 userProfileId;
    private Long likeCount;
    private List<Long> likePeople;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;


    public CommentDto(Comment comment, FilesResponseDto filesResponseDto) {
        UserProfile userProfile = comment.getUserProfile();
        UserProfileResponseDto2 userProfileDto = new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getIntroduction(),
                userProfile.getFiles(),
                filesResponseDto
        );
        this.boardId = comment.getBoard().getBoardId();
        this.commentId = comment.getCommentId();
        this.contents = comment.getContents();
        this.userProfileId = userProfileDto;
        this.likeCount = (long) comment.getLikes().size();
        this.likePeople = comment.getLikes().stream()
                .map(likes -> likes.getUserid().getUserProfileId())
                .collect(Collectors.toList());
        this.createAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();

    }
}
