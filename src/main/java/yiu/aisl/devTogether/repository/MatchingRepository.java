package yiu.aisl.devTogether.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Matching;
import java.util.List;
import java.util.Optional;
@Transactional


public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Optional<Matching> findByMatchingId(Long matchingId);


}