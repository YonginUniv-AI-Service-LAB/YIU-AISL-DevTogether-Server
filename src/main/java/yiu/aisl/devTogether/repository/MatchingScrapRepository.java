package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.MatchingScrap;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MatchingScrapRepository extends JpaRepository<MatchingScrap, Integer> {
    List<MatchingScrap> findByUser(User user);

    Optional<MatchingScrap> findByUserAndUserProfileAndStatus(User user, UserProfile userProfile, Integer status);

    List<MatchingScrap> deleteByUserAndUserProfileAndStatus(User user, UserProfile userProfile, Integer status);
}
