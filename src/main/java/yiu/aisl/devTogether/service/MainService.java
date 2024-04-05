package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.dto.RegisterDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.UserRepository;

@Service
@RequiredArgsConstructor// 주입받아야하는 final 필드에 대한 생성자를 생성
@Transactional
public class MainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public boolean register(RegisterDto request) throws Exception {

        //400 데이터 미입력
        if (request.getEmail() == null || request.getPwd() == null || request.getName() == null ||
                request.getRole() == null || request.getGender() == null || request.getAge() == null|| request.getMethod() == null
                || request.getFee() == null || request.getLocation1() == null || request.getLocation2() == null || request.getLocation3() == null)
        {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        //409 데이터 중복 (이메일)
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new CustomException(ErrorCode.DUPLICATE);
        }
        // 데이터 저장
        try {
            User user = User.builder()
                    .email(request.getEmail())
                    .pwd(passwordEncoder.encode(request.getPwd()))
                    .name(request.getName())
                    .role(request.getRole())
                    .gender(request.getGender())
                    .age(request.getAge())
                    .method(request.getMethod())
                    .fee(request.getFee())
                    .location1(request.getLocation1())
                    .location2(request.getLocation2())
                    .location3(request.getLocation3())
                    .build();
            userRepository.save(user);
        }
        catch (Exception e) {
            throw new Exception("서버 오류");
        }
        return true;
    }










}
