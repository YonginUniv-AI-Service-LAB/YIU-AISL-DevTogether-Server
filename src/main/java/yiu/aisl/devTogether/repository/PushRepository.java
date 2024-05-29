package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Push;
import yiu.aisl.devTogether.domain.User;

import java.util.List;

public interface PushRepository extends JpaRepository<Push, Long> {
    List<Push> findByUser(User userid);
}
