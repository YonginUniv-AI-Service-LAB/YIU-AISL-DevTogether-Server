package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.BoardScrap;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;


import java.util.List;
import java.util.Optional;

@Transactional
public interface BoardScrapRepository extends JpaRepository<BoardScrap, Integer> {
    List<BoardScrap> findByUser(UserProfile user);

    Optional<BoardScrap> findByUserAndBoard(UserProfile user, Board board);

    List<BoardScrap> deleteByUserAndBoard(UserProfile user, Board board);
}
