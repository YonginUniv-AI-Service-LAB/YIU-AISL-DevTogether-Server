package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.Review;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long matchingId;
    private String contents;
    private Boolean hide;
    private Integer category;

    public static ReviewResponseDto getReview(Review review) {
        return new ReviewResponseDto(
                review.getReviewId(),
                review.getMatchingId().getMatchingId(),
                review.getContents(),
                review.getHide(),
                review.getCategory()
        );
    }

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.matchingId = review.getMatchingId().getMatchingId();
        this.contents = review.getContents();
        this.hide = review.getHide();
        this.category = review.getCategory();
    }

}
