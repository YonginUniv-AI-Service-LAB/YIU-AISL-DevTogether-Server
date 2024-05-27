package yiu.aisl.devTogether.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;

import java.util.List;
import java.util.Optional;
@Transactional


public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Optional<Matching> findByMatchingId(Long matchingId);

    List<Matching> findByMentor(UserProfile user);
    List<Matching> findByMentee(UserProfile user);
}
