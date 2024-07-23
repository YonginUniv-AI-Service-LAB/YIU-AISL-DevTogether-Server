package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.Review;
import yiu.aisl.devTogether.domain.UserProfile;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long matchingId;
    private UserProfileResponseDto2 userProfileId;
    private String contents;
    private Boolean hide;
    private Integer star1;
    private Integer star2;
    private Integer star3;
    private Integer category;
    private LocalDateTime reviewCreatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

//    public static ReviewResponseDto getReview(Review review) {
//        return new ReviewResponseDto(
//                review.getReviewId(),
//                review.getMatchingId().getMatchingId(),
//                review.getContents(),
//                review.getHide(),
//                review.getStar1(),
//                review.getStar2(),
//                review.getStar3(),
//                review.getCategory()
//        );
//    }

    public ReviewResponseDto(Review review, Integer role, FilesResponseDto filesResponseDto) {
        UserProfile userProfile = null;
        if (role == 1) {
            userProfile = review.getMatchingId().getMentor();
        } else {
            userProfile = review.getMatchingId().getMentee();
        }

        UserProfileResponseDto2 userProfileDto = new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getIntroduction(),
                userProfile.getFiles(),
                filesResponseDto
        );
        this.reviewId = review.getReviewId();
        this.matchingId = review.getMatchingId().getMatchingId();
        this.userProfileId = userProfileDto;
        this.contents = review.getContents();
        this.hide = review.getHide();
        this.star1 = review.getStar1();
        this.star2 = review.getStar2();
        this.star3 = review.getStar3();
        this.category = review.getCategory();
        this.reviewCreatedAt = review.getCreatedAt();
        this.createdAt = review.getMatchingId().getCreatedAt();
        this.endedAt = review.getMatchingId().getEndedAt();
    }

}
