package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.FaqRequestDto;
import yiu.aisl.devTogether.service.FaqService;

import java.util.List;


@RestController
@RequiredArgsConstructor

public class FaqController {
    private final FaqService faqService;

    // faq 조회
    @GetMapping("/faq")
    public ResponseEntity<List> getList() throws Exception {
        return new ResponseEntity<List>(faqService.getList(), HttpStatus.OK);
    }
    // faq 등록
    @PostMapping("/faq")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> create(@AuthenticationPrincipal CustomUserDetails user, FaqRequestDto.CreateDTO request) throws Exception {
        System.out.println("faq_create request: " + request);
        return new ResponseEntity<Boolean>(faqService.create(user.getEmail(), request), HttpStatus.OK);
    }

    // faq 삭제
    @DeleteMapping("/faq")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails user,  FaqRequestDto.DeleteDTO request) throws Exception {
        System.out.println("faq_delete request" + request);
        return new ResponseEntity<Boolean>(faqService.delete(user.getEmail(), request), HttpStatus.OK);
    }

    // faq 수정
    @PutMapping("/faq")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user,  FaqRequestDto.UpdateDTO request) throws Exception {
        System.out.println("faq_update request" + request);
        return new ResponseEntity<Boolean>(faqService.update(user.getEmail(), request), HttpStatus.OK);
    }
}
