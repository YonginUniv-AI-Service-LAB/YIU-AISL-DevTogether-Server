package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.repository.UserRepository;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.User;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {


    //이메일로 사용자 정보를 조회한 후, 닉네임으로 사용자 정보를 추가적으로 조회
    private final UserRepository userRepository;

    @Value("${admin.email1}")
    private String admin1;


    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")
        );
        return new CustomUserDetails(user);
    }


    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")
        );
        CustomUserDetails userDetails = new CustomUserDetails(user);


        if (user.getEmail().equals(admin1)) {
            userDetails.setRole("ADMIN");
        } else {
            userDetails.setRole("USER");
        }


        return userDetails;
    }


}
