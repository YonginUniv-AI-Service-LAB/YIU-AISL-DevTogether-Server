package yiu.aisl.devTogether.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import yiu.aisl.devTogether.domain.User;


import java.util.Optional;


@Transactional



public interface UserRepository extends JpaRepository<User, Long> {





    //Optional은 값의 존재 여부를 나타내는 컨테이너 값이 존재할 때는 값을 반환하고, 값이 없을 때는 명시적으로 표현
    Optional<User> findByEmail(String email);

    Optional<User>findByName(String name);


    Optional<User>findByNameAndBirthAndQuestionAndAnswer(String name, String birth,  Integer question, String answer);
    Optional<User> findByRefreshToken(String refreshToken);



}