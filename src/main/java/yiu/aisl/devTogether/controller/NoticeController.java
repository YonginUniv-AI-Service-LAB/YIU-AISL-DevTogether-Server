package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.dto.NoticeRequestDto;
import yiu.aisl.devTogether.service.NoticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 전체 조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception {
        return new ResponseEntity<List>(noticeService.getList(), HttpStatus.OK);
    }

    // 공지사항 등록
    @PostMapping
    public ResponseEntity<Boolean> create(@ModelAttribute NoticeRequestDto.CreateDTO request) throws Exception {
        System.out.println("Notice_create request: " + request);
        return new ResponseEntity<Boolean>(noticeService.create(request), HttpStatus.OK);
    }

    // 공지사항 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@ModelAttribute NoticeRequestDto.DeleteDTO request) throws Exception {
        System.out.println("Notice_delete request" + request);
        return new ResponseEntity<Boolean>(noticeService.delete(request), HttpStatus.OK);
    }

    // 공지사항 수정
    @PutMapping
    public ResponseEntity<Boolean> update(@ModelAttribute NoticeRequestDto.UpdateDTO request) throws Exception {
        System.out.println("Notice_update request" + request);
        return new ResponseEntity<Boolean>(noticeService.update(request), HttpStatus.OK);
    }
}
