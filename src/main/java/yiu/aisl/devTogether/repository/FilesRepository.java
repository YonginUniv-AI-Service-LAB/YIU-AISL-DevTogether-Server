package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Files;

import java.util.List;
import java.util.Optional;

public interface FilesRepository extends JpaRepository<Files, Long> {
    List<Files> findByTypeAndTypeId(Integer type, Long typeId);

    Optional<Files> findByFileId(Long fileId);
}
