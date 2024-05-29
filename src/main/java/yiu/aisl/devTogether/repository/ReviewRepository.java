package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Review;
import yiu.aisl.devTogether.domain.UserProfile;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Review> findByUserProfileAndCategory(UserProfile userProfile, Integer category);

    Optional<Review> findByReviewId(Long reviewId);
}
