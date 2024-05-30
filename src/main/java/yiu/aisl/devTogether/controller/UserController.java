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
import yiu.aisl.devTogether.dto.UserProfileRequestDto;
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

    // 내 멘티 정보 조회
    @GetMapping(value = "/matching/mentee")
    public ResponseEntity<Object> getMyMentee(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyMentee(customUserDetails), HttpStatus.OK);
    }

    // 내 멘토 정보 조회
    @GetMapping(value = "/matching/mentor")
    public ResponseEntity<Object> getMyMentor(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyMentor(customUserDetails), HttpStatus.OK);
    }

    // 내 멘토 프로필 수정
    @PutMapping(value = "/mentor")
    public ResponseEntity<Boolean> changeMentorProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileRequestDto request) {
        return new ResponseEntity<Boolean>(userService.changeMentorProfile(customUserDetails, request), HttpStatus.OK);
    }

    // 내 멘티 프로필 수정
    @PutMapping(value = "/mentee")
    public ResponseEntity<Boolean> changeMenteeProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileRequestDto request) {
        return new ResponseEntity<Boolean>(userService.changeMenteeProfile(customUserDetails, request), HttpStatus.OK);
    }

    // 알림 확인
    @PutMapping(value = "/push")
    public ResponseEntity<Boolean> checkAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "id") Long id) {
        return new ResponseEntity<Boolean>(userService.checkAlarm(userDetails, id), HttpStatus.OK);
    }

    // 알림 내역 조회
    @GetMapping(value = "/push")
    public ResponseEntity<Object> getMyAlarm(@AuthenticationPrincipal CustomUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyAlarms(userDetails), HttpStatus.OK);
    }

    // 내가 스크랩한 게시글 조회
    @GetMapping(value = "/scrap/board")
    public ResponseEntity<Object> getMyBoardScrap(@AuthenticationPrincipal CustomUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMyScrap(userDetails), HttpStatus.OK);
    }

    // 내가 스크랩한 멘토 매칭 조회
    @GetMapping(value = "/scrap/mentor")
    public ResponseEntity<Object> getMyMentorMatchingScrap(@AuthenticationPrincipal CustomUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMentorProfileScrap(userDetails), HttpStatus.OK);
    }

    // 내가 스크랩한 멘티 매칭 조회
    @GetMapping(value = "/scrap/mentee")
    public ResponseEntity<Object> getMyMenteeMatchingScrap(@AuthenticationPrincipal CustomUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(userService.getMenteeProfileScrap(userDetails), HttpStatus.OK);
    }
}
