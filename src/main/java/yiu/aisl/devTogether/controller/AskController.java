package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.AskRequestDto;
import yiu.aisl.devTogether.service.AskService;


import java.util.List;

@RestController
@RequiredArgsConstructor

public class AskController {
    private final AskService askService;

    // ask 조회(내가 쓴 글 조회)
    @GetMapping("/ask")
    public ResponseEntity<List> getList(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(askService.getList(user.getEmail()), HttpStatus.OK);
    }

    // ask 조회(관리자용)
    @GetMapping("/admin/ask")
    public ResponseEntity<List> getAdminList() throws Exception {
        return new ResponseEntity<List>(askService.getAdminList(), HttpStatus.OK);
    }


    // ask 등록
    @PostMapping("/ask")
    public ResponseEntity<Boolean> create(@AuthenticationPrincipal CustomUserDetails user, AskRequestDto.CreateDTO request, List<MultipartFile> file) throws Exception {
        System.out.println("ask_create request: " + request);
        return new ResponseEntity<Boolean>(askService
                .create(user.getEmail(), request, file), HttpStatus.OK);
    }

    // ask 답변
    @PostMapping("/admin/ask/answer")
    public ResponseEntity<Boolean> answer(@AuthenticationPrincipal CustomUserDetails user, @ModelAttribute("askId") Long askId, AskRequestDto.AnswerDTO request) throws Exception {
        System.out.println("ask_answer request: " + request);
        return new ResponseEntity<Boolean>(askService.answer(user.getEmail(), askId, request), HttpStatus.OK);
    }

    // ask 삭제
    @DeleteMapping("/ask")
    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails user, AskRequestDto.DeleteDTO request, List<MultipartFile> file) throws Exception {
        System.out.println("ask_delete request" + request);
        return new ResponseEntity<Boolean>(askService.delete(user.getEmail(), request), HttpStatus.OK);
    }

    // ask 수정
    @PutMapping("/ask")
    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user, AskRequestDto.UpdateDTO request, List<MultipartFile> file) throws Exception {
        System.out.println("ask_update request" + request);
        return new ResponseEntity<Boolean>(askService.update(user.getEmail(), request, file), HttpStatus.OK);
    }
}
