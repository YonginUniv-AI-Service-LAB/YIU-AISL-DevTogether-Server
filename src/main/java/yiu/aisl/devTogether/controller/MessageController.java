package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.MessageRequestDto;
import yiu.aisl.devTogether.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/message")
public class MessageController {
    private final MessageService messageService;

    //쪽지 보내기
    @PostMapping
    public ResponseEntity<Boolean> sendMessage(@AuthenticationPrincipal CustomUserDetails user, MessageRequestDto.sendDto request) throws Exception {
        return new ResponseEntity<Boolean>(messageService.send(user.getEmail(), request), HttpStatus.OK);
    }
    //쪽지 조회
    @GetMapping
    public ResponseEntity<List> getMessage(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(messageService.getAll(user.getEmail()), HttpStatus.OK);
    }
//    //쪽지 삭제
//    @DeleteMapping
//    public ResponseEntity<Boolean> deleteMessage(@AuthenticationPrincipal CustomUserDetails user, MessageRequestDto.sendDto request){
//        return new ResponseEntity<Boolean>(messageService.delete(user.getEmail(), request), HttpStatus.OK);
//    }
}
