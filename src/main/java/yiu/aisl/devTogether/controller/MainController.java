package yiu.aisl.devTogether.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.service.MainService;

@RestController  //controller + responsebody > json형태로 객체 반환
@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자

//controller과 restController 차이점
//controller: 뷰를 반환, HTML 뷰를 반환
//restController: 데이터를 반환, JSON, XML 형식의 데이터 반환




public class MainController {

    private final MainService mainService;

    //ResponseEntity는 HTTP 응답을 나타내는 Spring 클래스

    //회원가입        회원가입을 성공했냐 안 했냐 유무를 가지고 판별해야하므로 Bool타입 사용
    @PostMapping(value = "/register" )
    public ResponseEntity<Boolean> register(RegisterRequestDto request) throws Exception{
        //@RequestBody를 사용하지 않았기 때문에 x-www-form-urlencoded 이 데이터 형식 사용 가능
        System.out.println(" registration request: " + request);
        return new ResponseEntity<Boolean>(mainService.register(request), HttpStatus.OK);
    }
    //로그인   사용자에 대한 정보를 포함한 토큰이 필요함 따라서 LoginResponseDto로 반환해야함
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto>login(LoginRequestDto request) throws  Exception{
        System.out.println("login request" + request);
        return new ResponseEntity<LoginResponseDto>(mainService.login(request), HttpStatus.OK);
    }
    //회원가입 시 이메일 인증
    @PostMapping(value = "/register/email")
    public ResponseEntity<String>registerEmail(EmailRequestDto request) throws  Exception{
        System.out.println("registerEmail request" + request);
        return new ResponseEntity<String>(mainService.registerEmail(request.getEmail()), HttpStatus.OK);
    }
    //비밀번호 변경 시 이메일 인증
    @PostMapping(value = "/pwd/email")
    public ResponseEntity<String>pwdEmail(EmailRequestDto request) throws  Exception{
        System.out.println("pwdEmail request" + request);
        return new ResponseEntity<String>(mainService.pwdEmail(request.getEmail()), HttpStatus.OK);
    }
    //비밀번호 변경
    @PostMapping(value = "/pwd/change")
    public ResponseEntity<Boolean> pwdChange(PwdChangeRequestDto request) throws  Exception{
        System.out.println("pwdChange request" + request);
        return new ResponseEntity<Boolean>(mainService.pwdChange(request), HttpStatus.OK);
    }
    //닉네임 변경
    @PostMapping(value = "/nickname")
    public ResponseEntity<Boolean> nicknameCheck(NicknameCheckRequestDto request) throws  Exception{
        System.out.println("nicknameCheck request" + request);
        return new ResponseEntity<Boolean>(mainService.nicknameCheck(request), HttpStatus.OK);
    }

    //email 찾기
    @PostMapping(value = "/email")
    public ResponseEntity<Boolean>  emailFind(@AuthenticationPrincipal CustomUserDetails user,EmailDto request) throws  Exception{
        return new ResponseEntity<Boolean>(mainService.emailFind(user.getEmail(),request), HttpStatus.OK);
    }

    //refresh토큰
    @PostMapping(value = "/refresh")
    public ResponseEntity<TokenDto>  createNewAccessToken(TokenDto tokenDto) throws  Exception{
        return new ResponseEntity<>(mainService.refreshAccessToken(tokenDto), HttpStatus.OK);
    }

}