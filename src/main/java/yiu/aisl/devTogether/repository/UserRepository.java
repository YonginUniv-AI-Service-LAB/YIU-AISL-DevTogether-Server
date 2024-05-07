package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import yiu.aisl.devTogether.domain.User;


import java.util.Optional;
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User>findByNickname(String name);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByStudentId(Long userId);
}
