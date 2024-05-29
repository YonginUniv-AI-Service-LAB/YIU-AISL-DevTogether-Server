package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Report;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByReportId(Long reportId);
}
