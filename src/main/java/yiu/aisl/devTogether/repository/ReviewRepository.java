package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.Review;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMatchingIdAndCategoryAndHide(Matching matching, Integer category,Boolean hide );
    List<Review> findByMatchingIdAndCategory(Matching matching, Integer category );
    List<Review> findByMatchingId(Matching matching);

    Optional<Review> findByReviewId(Long reviewId);
}
