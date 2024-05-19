package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Notice;
import yiu.aisl.devTogether.domain.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
