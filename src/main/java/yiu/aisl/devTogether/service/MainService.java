package yiu.aisl.devTogether.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Token;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.TokenRepository;
import yiu.aisl.devTogether.repository.UserRepository;
import yiu.aisl.devTogether.security.TokenProvider;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor// 주입받아야하는 final 필드에 대한 생성자를 생성
@Transactional
public class MainService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    private long exp_refreshToken = Duration.ofDays(14).toMillis(); // 만료시간 2주

    //회원가입
    public boolean register(RegisterDto request) throws Exception {

        //400 데이터 미입력
        if ( request.getEmail() == null || request.getPwd() == null || request.getName() == null
                || request.getNickname() == null   || request.getRole() == null   || request.getGender() == null
                || request.getImg() == null   || request.getAge() == null   || request.getPhone() == null
                || request.getLocation1() == null   || request.getLocation2() == null   || request.getLocation3() == null
        )
        {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        //409 데이터 중복 (이메일)
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new CustomException(ErrorCode.DUPLICATE);
        }
        //409 데이터 중복 (닉네임)
        if (userRepository.findByNickname(request.getNickname()).isPresent()) {

            throw new CustomException(ErrorCode.DUPLICATE);
        }

        // 데이터 저장
        try {
            User user = User.builder()
                    .email(request.getEmail())
                    .pwd(passwordEncoder.encode(request.getPwd()))
                    .name(request.getName())
                    .name(request.getNickname())
                    .role(request.getRole())
                    .gender(request.getGender())
                    .img(request.getImg())
                    .age(request.getAge())
                    .phone(request.getPhone())
                    .location1(request.getLocation1())
                    .location2(request.getLocation2())
                    .location3(request.getLocation3())
                    .build();
            userRepository.save(user);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        // 400 데이터 미입력
        if (request.getEmail() == null || request.getPwd() == null || request.getRole() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 404 회원 없음(회원정보 확인)
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXIST));

        // 401 회원정보 불일치
        if (!passwordEncoder.matches(request.getPwd(), user.getPwd()) || !Objects.equals(request.getRole(), user.getRole())) {
            throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
        }

        try {
            // 토큰 발급 (추가 정보 확인 하기 위해 이름 포함 시킴)
            user.setRefreshToken(createRefreshToken(user));

            LoginResponseDto response = LoginResponseDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .token(TokenDto.builder()
                            .accessToken(tokenProvider.createToken(user))
                            .refreshToken(user.getRefreshToken())
                            .build())
                    .build();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

        }
    }


















    public TokenDto refreshAccessToken(TokenDto token) throws Exception {
        String email = null;
        try {
            email = tokenProvider.getEmail(token.getAccessToken());
        } catch (ExpiredJwtException e) {
            email = e.getClaims().get("email", String.class);
        }

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.MEMBER_NOT_EXIST));

        Token refreshToken = validRefreshToken(user, token.getRefreshToken());

        try {
            if (refreshToken != null) {
                return TokenDto.builder()
                        .accessToken(tokenProvider.createToken(user))
                        .refreshToken(refreshToken.getRefreshToken())
                        .build();
            } else {
                throw new CustomException(ErrorCode.LOGIN_REQUIRED);
            }
        }
        catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    private String createRefreshToken(User user) {
        Token token = tokenRepository.save(
                Token.builder()
                        .email(user.getEmail())
                        .refreshToken(UUID.randomUUID().toString())
                        .expiration(exp_refreshToken)
                        .build()
        );
        return token.getRefreshToken();
    }

    public Token validRefreshToken(User user, String refreshToken) throws Exception {
        Token token = tokenRepository.findById(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_REQUIRED));

        if(token.getRefreshToken() == null) throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        try{
            if(token.getExpiration() < 10){
                token.setExpiration(1000L);
                tokenRepository.save(token);
            }
            if(!token.getRefreshToken().equals(refreshToken)){
                throw new CustomException(ErrorCode.LOGIN_REQUIRED);
            }else{
                return token;
            }
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
