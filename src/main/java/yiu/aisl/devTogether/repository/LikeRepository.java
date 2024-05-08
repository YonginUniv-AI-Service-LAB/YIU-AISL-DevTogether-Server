package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.Like;
import yiu.aisl.devTogether.domain.User;

import java.util.Optional;

@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUseridEmail(User email);


}
