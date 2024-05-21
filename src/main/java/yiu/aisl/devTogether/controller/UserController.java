package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.dto.MyProfileRequestDto;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.service.UserService;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 내 정보 조회
    @GetMapping(value = "")
    public ResponseEntity<Object> getMyProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyProfile(customUserDetails), HttpStatus.OK);
    }

    // 내 정보 수정
    @PutMapping(value = "")
    public ResponseEntity<Boolean> updateProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MyProfileRequestDto dto) throws Exception {
        return new ResponseEntity<Boolean>(userService.updateProfile(customUserDetails, dto), HttpStatus.OK);
    }

    // 내가 작성한 댓글 조회
    @GetMapping(value = "/comment")
    public ResponseEntity<Object> getMyComment(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyComment(customUserDetails), HttpStatus.OK);
    }

    // 내가 작성한 글 조회
    @GetMapping(value = "/board")
    public ResponseEntity<Object> getMyBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyBoard(customUserDetails), HttpStatus.OK);
    }

    // 내가 스크랩한 글 조회
//    @GetMapping(value = "/board/scrap")
//    public ResponseEntity<Object> getMyScrap(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//        return new ResponseEntity<>(userService.getMyScrap(customUserDetails), HttpStatus.OK);
//    }
}
