package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Like;
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {


}
