package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.AskRequestDto;
import yiu.aisl.devTogether.service.AskService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ask")
public class AskController {
    private final AskService askService;

    // ask 조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception {
        return new ResponseEntity<List>(askService.getList(), HttpStatus.OK);
    }
    // ask 등록
    @PostMapping

    public ResponseEntity<Boolean> create(@AuthenticationPrincipal CustomUserDetails user, AskRequestDto.CreateDTO request ) throws Exception {

        System.out.println("ask_create request: " + request);
        return new ResponseEntity<Boolean>(askService.create( user.getEmail(),request), HttpStatus.OK);
    }
    // ask 답변
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/answer")
    public ResponseEntity<Boolean> answer(@AuthenticationPrincipal CustomUserDetails user,@ModelAttribute("askId") Long askId,  AskRequestDto.AnswerDTO request) throws Exception {
        System.out.println("ask_answer request: " + request);
        return new ResponseEntity<Boolean>(askService.answer(user.getEmail(),askId, request), HttpStatus.OK);
    }

    // ask 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete( @AuthenticationPrincipal CustomUserDetails user,AskRequestDto.DeleteDTO request) throws Exception {
        System.out.println("ask_delete request" + request);
        return new ResponseEntity<Boolean>(askService.delete(user.getEmail(),request), HttpStatus.OK);
    }

    // ask 수정
    @PutMapping
    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user,  AskRequestDto.UpdateDTO request) throws Exception {
        System.out.println("ask_update request" + request);
        return new ResponseEntity<Boolean>(askService.update(user.getEmail(),request), HttpStatus.OK);
    }
}
