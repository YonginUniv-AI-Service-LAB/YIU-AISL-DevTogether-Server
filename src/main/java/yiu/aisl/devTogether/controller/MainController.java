package yiu.aisl.devTogether.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.service.MainService;

@RestController  //controller + responsebody > json형태로 객체 반환
@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자


public class MainController {

    private final MainService mainService;

    //ResponseEntity는 HTTP 응답을 나타내는 Spring 클래스
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    public ResponseEntity<Boolean> register( RegisterDto request) throws Exception{
        System.out.println(" registration request: " + request);
        return new ResponseEntity<Boolean>(mainService.register(request), HttpStatus.OK);
    }


    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoginResponseDto>login(LoginRequestDto request) throws  Exception{
        System.out.println("login request" + request);
        return new ResponseEntity<LoginResponseDto>(mainService.login(request), HttpStatus.OK);
    }
    @PostMapping(value = "/register/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String>registerEmail(EmailRequestDto request) throws  Exception{
        System.out.println("registerEmail request" + request);
        return new ResponseEntity<String>(mainService.registerEmail(request.getEmail()), HttpStatus.OK);
    }
    @PostMapping(value = "/pwd/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String>pwdEmail(EmailRequestDto request) throws  Exception{
        System.out.println("pwdEmail request" + request);
        return new ResponseEntity<String>(mainService.pwdEmail(request.getEmail()), HttpStatus.OK);
    }
    @PostMapping(value = "/pwd/change", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> pwdChange(PwdChangeRequestDto request) throws  Exception{
        System.out.println("pwdChange request" + request);
        return new ResponseEntity<Boolean>(mainService.pwdChange(request), HttpStatus.OK);
    }
    @PostMapping(value = "/nickname", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Boolean> nicknameCheck(NicknameCheckRequestDto request) throws  Exception{
        System.out.println("nicknameCheck request" + request);
        return new ResponseEntity<Boolean>(mainService.nicknameCheck(request), HttpStatus.OK);
    }

}