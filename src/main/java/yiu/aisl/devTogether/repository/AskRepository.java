package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Ask;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AskRepository extends JpaRepository<Ask, Long> {

    List<Ask> findByOrderByCreatedAtDesc();
    Optional<Ask> findByAskId(Long id);
}
