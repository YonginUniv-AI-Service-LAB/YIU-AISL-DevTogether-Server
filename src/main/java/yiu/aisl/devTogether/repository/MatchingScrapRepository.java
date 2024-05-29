package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.MatchingScrap;
import yiu.aisl.devTogether.domain.User;

import java.util.List;

@Transactional
public interface MatchingScrapRepository extends JpaRepository<MatchingScrap, Integer> {
    List<MatchingScrap> findByUser(User user);
}
