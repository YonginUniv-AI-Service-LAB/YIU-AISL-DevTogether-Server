package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Message;
import yiu.aisl.devTogether.domain.User;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByToUserid(User toUser);
    List<Message> findByFromUserId(User fromUser);
}
