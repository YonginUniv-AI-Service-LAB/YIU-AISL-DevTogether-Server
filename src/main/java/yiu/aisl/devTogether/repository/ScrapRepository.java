package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Scrap;
import yiu.aisl.devTogether.domain.User;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByUser(User user);
}
