package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Likes;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;

import java.util.Optional;

@Transactional
public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByuserid(User user);

    Optional<Likes> findByUseridAndTypeId(UserProfile user, Long typeId);

}
