package yiu.aisl.devTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
