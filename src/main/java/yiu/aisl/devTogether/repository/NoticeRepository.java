package yiu.aisl.devTogether.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Notice;

import java.util.List;
import java.util.Optional;


@Transactional
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByOrderByCreatedAtDesc();
    Optional<Notice> findByNoticeId(Long noticeId);


}
