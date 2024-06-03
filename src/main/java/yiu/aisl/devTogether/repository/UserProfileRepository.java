package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.util.List;
import java.util.Optional;


@Transactional

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


    Optional<UserProfile> findByUserProfileId(Long userProfileId);

    Optional<UserProfile> findByUserIdAndRole(Long userId, Integer role);

    List<UserProfile> findByRole(Integer role);

    Optional<UserProfile> findByUser(User user);

    Optional<UserProfile> findByUserAndRole(User user, Integer role);



}