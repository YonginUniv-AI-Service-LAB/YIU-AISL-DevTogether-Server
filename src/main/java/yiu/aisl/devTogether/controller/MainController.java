package yiu.aisl.devTogether.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.service.FilesService;
import yiu.aisl.devTogether.service.MainService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController  //controller + responsebody > json형태로 객체 반환
@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자

//controller과 restController 차이점
//controller: 뷰를 반환, HTML 뷰를 반환
//restController: 데이터를 반환, JSON, XML 형식의 데이터 반환




public class MainController {

    private final MainService mainService;
    private final FilesService filesService;

    //ResponseEntity는 HTTP 응답을 나타내는 Spring 클래스




    // 과목 프론트로 넘겨주기
    @GetMapping(value = "/subject")
    public ResponseEntity<List<String>> subject() {
        List<String> subjects = mainService.getSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
    //메인
    @GetMapping("/main")
    public ResponseEntity<Object> getList() throws Exception {
        return new ResponseEntity<>(mainService.getList(), HttpStatus.OK);
    }

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
    //멘토 닉네임 변경
    @PostMapping(value = "/mentor/nickname")
    public ResponseEntity<Boolean> mentorNicknameCheck(NicknameCheckRequestDto request) throws  Exception{
        System.out.println("nicknameCheck request" + request);
        return new ResponseEntity<Boolean>(mainService.mentorNicknameCheck(request), HttpStatus.OK);
    }
    //멘티 닉네임 변경
    @PostMapping(value = "/mentee/nickname")
    public ResponseEntity<Boolean> menteeNicknameCheck(NicknameCheckRequestDto request) throws  Exception{
        System.out.println("nicknameCheck request" + request);
        return new ResponseEntity<Boolean>(mainService.menteeNicknameCheck(request), HttpStatus.OK);
    }

    //email 찾기
    @PostMapping(value = "/email")
    public ResponseEntity<String >  emailFind( EmailDto request) throws  Exception{
        return new ResponseEntity<String >(mainService.emailFind(request), HttpStatus.OK);
    }

    // accessToke 재발급
    @PostMapping(value = "/token/refresh")
    public ResponseEntity<TokenDto>  createNewAccessToken(TokenDto tokenDto) throws  Exception{
        return new ResponseEntity<>(mainService.refreshAccessToken(tokenDto), HttpStatus.OK);
    }

    // 멘토&멘티 변경시 accessToken 재발급
    @PostMapping(value = "/token/change")
    public ResponseEntity<TokenDto> createUserChangeAccessToken(TokenDto tokenDto) throws Exception {
        return new ResponseEntity<>(mainService.changeAccessToken(tokenDto), HttpStatus.OK);
    }

    // role 추가 (멘토 또는 멘티 값 추가)
    @PutMapping(value = "/role")
    public ResponseEntity<Boolean> addRole(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<Boolean>(mainService.addRole(userDetails), HttpStatus.OK);
    }


    //파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<FilesResponseDto> downloadFile(Long fileId) throws Exception {
        return new ResponseEntity<FilesResponseDto>(filesService.downloadFile(fileId), HttpStatus.OK);
    }


}