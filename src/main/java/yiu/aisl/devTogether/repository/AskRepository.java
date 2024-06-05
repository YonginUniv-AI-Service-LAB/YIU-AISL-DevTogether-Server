package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.User;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AskRepository extends JpaRepository<Ask, Long> {

    List<Ask> findByOrderByCreatedAtDesc();
    Optional<Ask> findByAskId(Long askId);
    List<Ask> findByUser(User user);

}

// db에 접근 하는데 사용하며 JpaRepository를 확장함으로써 CRUD 기능을 제공받음