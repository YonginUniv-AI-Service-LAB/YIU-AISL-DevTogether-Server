package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.BoardScrap;
import yiu.aisl.devTogether.domain.Likes;
import yiu.aisl.devTogether.domain.UserProfile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardScrapDto {
    private Integer id;
    private Integer board;
    private String title;
    private String contents;
    private UserProfileResponseDto2 userProfileId;
    private Integer likes;
    private Integer countComment;
    private List<Long> likePeople;
    private List<Long> scrapPeople;
    private LocalDateTime createdAt;

    public BoardScrapDto(BoardScrap scrap) {
        UserProfile userProfile = scrap.getBoard().getUserProfile();
        UserProfileResponseDto2 userProfileDto = new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getIntroduction(),
                userProfile.getFiles(),
                userProfile.getFilesdata()
        );
        this.id = scrap.getId();
        this.board = Math.toIntExact(scrap.getBoard().getBoardId());
        this.title = scrap.getBoard().getTitle();
        this.contents = scrap.getBoard().getContents();
        this.userProfileId = userProfileDto;
        this.createdAt = scrap.getCreatedAt();
        this.likes = scrap.getBoard().getLikes().size();
        this.countComment = scrap.getBoard().getComments().size();
        this.likePeople =scrap.getBoard().getLikes().stream()
                .map(likes -> likes.getUserid().getUserProfileId())
                .collect(Collectors.toList());
        this.scrapPeople=  scrap.getBoard().getBoardScraps().stream()
                .map(boardScrap -> boardScrap.getUser().getUserProfileId())
                .collect(Collectors.toList());
    }
}
