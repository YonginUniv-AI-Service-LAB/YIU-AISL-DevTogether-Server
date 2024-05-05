package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return new ResponseEntity<>(noticeService.getList(), HttpStatus.OK);
    }

    // 공지사항 등록
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Boolean> create(@ModelAttribute NoticeRequestDto.CreateDTO request) throws Exception {
        System.out.println("Notice_create request: " + request);
        return new ResponseEntity<>(noticeService.create(request), HttpStatus.OK);
    }

    // 공지사항 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@ModelAttribute NoticeRequestDto.DeleteDTO request) throws Exception {
        System.out.println("Notice_delete request" + request);
        return new ResponseEntity<>(noticeService.delete(request), HttpStatus.OK);
    }

    // 공지사항 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Boolean> update(@ModelAttribute NoticeRequestDto.UpdateDTO request) throws Exception {
        System.out.println("Notice_update request" + request);
        return new ResponseEntity<>(noticeService.update(request), HttpStatus.OK);
    }
}
