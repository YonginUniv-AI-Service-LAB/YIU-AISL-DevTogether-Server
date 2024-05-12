package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.dto.FaqRequestDto;
import yiu.aisl.devTogether.service.FaqService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    private final FaqService faqService;

    // faq 조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception {
        return new ResponseEntity<List>(faqService.getList(), HttpStatus.OK);
    }
    // faq 등록
    @PostMapping
    public ResponseEntity<Boolean> create(@ModelAttribute FaqRequestDto.CreateDTO request) throws Exception {
        System.out.println("faq_create request: " + request);
        return new ResponseEntity<Boolean>(faqService.create(request), HttpStatus.OK);
    }

    // faq 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@ModelAttribute FaqRequestDto.DeleteDTO request) throws Exception {
        System.out.println("faq_delete request" + request);
        return new ResponseEntity<Boolean>(faqService.delete(request), HttpStatus.OK);
    }

    // faq 수정
    @PutMapping
    public ResponseEntity<Boolean> update(@ModelAttribute FaqRequestDto.UpdateDTO request) throws Exception {
        System.out.println("faq_update request" + request);
        return new ResponseEntity<Boolean>(faqService.update(request), HttpStatus.OK);
    }
}
