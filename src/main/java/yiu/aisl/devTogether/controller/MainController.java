package yiu.aisl.devTogether.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import yiu.aisl.devTogether.dto.LoginRequestDto;
import yiu.aisl.devTogether.dto.LoginResponseDto;
import yiu.aisl.devTogether.dto.RegisterDto;
import yiu.aisl.devTogether.service.MainService;

@RestController  //controller + responsebody > json형태로 객체 반환
@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자


public class MainController {

    private final MainService mainService;



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

}
