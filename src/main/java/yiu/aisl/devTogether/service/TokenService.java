package yiu.aisl.devTogether.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final UserRepository userRepository;


    public User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("문제 발생"));
    }
}
