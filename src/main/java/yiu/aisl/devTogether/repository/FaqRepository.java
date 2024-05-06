package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Faq;


import java.util.List;
import java.util.Optional;
@Transactional
public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByOrderByCreatedAtDesc();
    Optional<Faq> findByFaqId(Long faqId);


}

