package yiu.aisl.devTogether.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.repository.UserRepository;
@RequiredArgsConstructor
@Service
public class TokenService {

    private final UserRepository userRepository;

    //refreshToken에 해당하는 사용자가 있는 경우 해당 사용자를 반환하고, 없는 경우에는 예외를 발생
    public User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

}
