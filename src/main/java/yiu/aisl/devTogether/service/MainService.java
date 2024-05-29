package yiu.aisl.devTogether.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import yiu.aisl.devTogether.domain.Token;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.QuestionCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.security.TokenProvider;
import yiu.aisl.devTogether.repository.*;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor// 주입받아야하는 final 필드에 대한 생성자를 생성
@Transactional
public class MainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    private final JavaMailSender javaMailSender;
    private final FilesService filesService;

    private long exp_refreshToken = Duration.ofDays(14).toMillis(); // 만료시간 2주
    private String authNum;


    //회원가입
    public Boolean register(RegisterRequestDto request) throws Exception {
        RoleCategory roleCategory = RoleCategory.fromInt(request.getRole());

        GenderCategory genderCategory = GenderCategory.fromInt(request.getGender());

        QuestionCategory questionCategory = QuestionCategory.fromInt(request.getQuestion());
        //400 - 데이터 미입력
        if (  request.getEmail().isEmpty() || request.getPwd().isEmpty() || request.getName().isEmpty()
                || request.getNickname().isEmpty()   || request.getRole() == null   || request.getGender() == null
                || request.getAge() == null || request.getBirth().isEmpty()  || request.getQuestion()== null    || request.getAnswer().isEmpty()                        )
        {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        //409 - 데이터 중복 (이메일)
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new CustomException(ErrorCode.DUPLICATE);
        }
        //409 - 데이터 중복 (닉네임)
        if (userRepository.findByNickname(request.getNickname()).isPresent()) {

            throw new CustomException(ErrorCode.DUPLICATE);
        }

        // 데이터 저장
        try {

            User user = User.builder()
                    .email(request.getEmail())
                    .pwd(passwordEncoder.encode(request.getPwd()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .role(roleCategory)
                    .gender(genderCategory)
                    .age(request.getAge())
                    .birth(request.getBirth())
                    .question(questionCategory)
                    .answer(request.getAnswer())
                    .build();
            userRepository.save(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    // 이메일 찾기
    public Boolean emailFind(String email,EmailDto request) throws Exception {
        User user = findByEmail(email);
        QuestionCategory questionCategory = QuestionCategory.fromInt(request.getQuestion());
        // 404 - 회원없음
        if (!user.getName().equals(request.getName()) ||
                !user.getBirth().equals(request.getBirth())) {
            throw new CustomException(ErrorCode.NOT_EXIST_MEMBER);
        }

        // 400 - 데이터 미입력
        if (request.getName().isEmpty() || request.getBirth().isEmpty()
                || request.getQuestion() == null  || request.getAnswer().isEmpty()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 401 - 정보 불일치
        if(!user.getQuestion().equals(questionCategory) || !user.getAnswer().equals(request.getAnswer())) {
            throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
        }
        return true;
    }

    //로그인
    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        RoleCategory roleCategory = RoleCategory.fromInt(request.getRole());
        int role = 0;
        // 400 - 데이터 미입력
        if (request.getEmail().isEmpty()|| request.getPwd().isEmpty() || request.getRole() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 404 - 회원 없음
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        // 401 - 회원정보 불일치

        if (user.getRole() == RoleCategory.멘토멘티) {
            if (!passwordEncoder.matches(request.getPwd(), user.getPwd())) {
                throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
            }
            if(request.getRole() == 1) {
                role = 1;
            } else role = 2;
        }
        if(user.getRole() == RoleCategory.멘토 || user.getRole() == RoleCategory.멘티){
            if (!passwordEncoder.matches(request.getPwd(), user.getPwd()) || !user.getRole().equals(roleCategory)) {
                throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
            }
            role = request.getRole();
        }


        try {
            // 토큰 발급 (추가 정보 확인 하기 위해 이름 포함 시킴)
            user.setRefreshToken(createRefreshToken(user));
            LoginResponseDto response = LoginResponseDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .token(TokenDto.builder()
                            .accessToken(tokenProvider.createToken(user, role))
                            .refreshToken(user.getRefreshToken())
                            .build())
                    .build();

            return response;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

        }
    }




    //회원가입 시 이메일 인증
    public String registerEmail(String email) throws MessagingException, UnsupportedEncodingException {
        if(email == null) throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        MimeMessage emailForm = createEmailForm(email + "@naver.com", "devTogether 회원가입 인증번호");
        javaMailSender.send(emailForm);
        return authNum;
    }
    //비밀번호 변경 시 이메일 인증
    public String pwdEmail(String email) throws MessagingException, UnsupportedEncodingException {
        if(email == null) throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        MimeMessage emailForm = createEmailForm(email + "@naver.com", "비밀번호 재설정을 위한 안내");
        javaMailSender.send(emailForm);
        return authNum;
    }
    private MimeMessage createEmailForm(String email, String title) throws MessagingException, UnsupportedEncodingException {
        // 코드 생성
        createCode();
        String setFrom = "yiuaiservicelab@naver.com";
        String toEmail = email;
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        // 메일 내용 설정
        String msgOfEmail = "";
        msgOfEmail += "<div style='margin: 20px;'>";
        msgOfEmail += "<h1>안녕하세요 용인대학교 devTogether 입니다.</h1>";
        msgOfEmail += "<br>";
        if (title.equals("devTogether 회원가입 인증번호")) {
            msgOfEmail += "<p><strong>devTogether 가입을 위한 인증번호입니다.</strong><p>";
        } else if (title.equals("비밀번호 재설정을 위한 안내")) {
            msgOfEmail += "<p><strong>비밀번호 재설정을 위한 안내입니다.</strong><p>";
        }
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 인증번호를 확인하여 이메일 주소 인증을 완료해주세요.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border-top: 1px solid black; border-bottom: 1px solid black; font-family: verdana;'>";
        msgOfEmail += "<div style='font-size: 150%; justify-content: center; align-items: center; display: flex; flex-direction: column; height: 100px;'>";
        msgOfEmail += "<strong style='color: blue; margin-top: auto; margin-bottom: auto;'>";
        msgOfEmail += authNum + "</strong>";
        msgOfEmail += "</div>";
        msgOfEmail += "</div>";
        msgOfEmail += "</div>";
        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");
        return message;
    }


    public void createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for(int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            key.append(digit);
        }
        authNum = key.toString();
    }
    //비밀번호 변경
    public Boolean pwdChange(PwdChangeRequestDto request)  throws MessagingException, UnsupportedEncodingException{
        // 400 - 데이터 없음
        if(request.getEmail().isEmpty()|| request.getPwd().isEmpty())
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        // 404 - 회원 존재 확인
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        try {
            user.setPwd(passwordEncoder.encode(request.getPwd()));
            userRepository.save(user);
        }
        catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
    //닉네임 중복
    public Boolean nicknameCheck(NicknameCheckRequestDto request) throws Exception{
        // 409 데이터 중복
        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE);
        }
        return true;

    }



//    public TokenDto refreshAccessToken(TokenDto token) throws Exception {
//        String email = null;
//        try {
//            email = tokenProvider.getEmail(token.getAccessToken());
//        }//만료된 경우
//        catch (ExpiredJwtException e) {
//            email = e.getClaims().get("email", String.class);
//        }
//        //이메일을 사용하여 해당 사용자를 db에서 찾음
//        User user = userRepository.findByEmail(email).orElseThrow(() ->
//                new CustomException(ErrorCode.NOT_EXIST_MEMBER));
//        //유효성 검사
//        Token refreshToken = validRefreshToken(user, token.getRefreshToken());
//        int role = 0;
//
//        if (user.getRole() == RoleCategory.멘토멘티) {
//
//        }
//        if(user.getRole() == RoleCategory.멘토 || user.getRole() == RoleCategory.멘티){
//
//        }
//
//        try {
//            if (refreshToken != null) {    //유효하다면 토큰 생성
//                return TokenDto.builder()
//                        .accessToken(tokenProvider.createToken(user, role))
//                        .refreshToken(refreshToken.getRefreshToken())
//                        .build();
//            }//로그인 필요
//            else {
//                throw new CustomException(ErrorCode.LOGIN_REQUIRED);
//            }
//        }
//        catch (Exception e) {
//            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }
    // refresh token을 생성
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
    //유효성 검사
    public Token validRefreshToken(User user, String refreshToken) throws Exception {
        //토큰 없을 시 로그인 필요
        Token token = tokenRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_REQUIRED));

        //RefreshToken 만료
        if(token.getRefreshToken() == null) throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        try{
            if(token.getExpiration() < 10){   //10초
                token.setExpiration(1000L); //1000초
                tokenRepository.save(token);
            }

            //로그인 필요
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


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }


}