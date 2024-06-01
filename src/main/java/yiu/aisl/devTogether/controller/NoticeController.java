package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.NoticeRequestDto;
import yiu.aisl.devTogether.dto.NoticeResponseDto;
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
    //공지사항 상세조회
    @GetMapping("/detail")
    public ResponseEntity<NoticeResponseDto> getDetail(@ModelAttribute("id") NoticeRequestDto.DetailDTO request) throws Exception {
        return new ResponseEntity<NoticeResponseDto>(noticeService.getDetail(request), HttpStatus.OK);
    }


    // 공지사항 등록
    @PostMapping
    public ResponseEntity<Boolean> create(@AuthenticationPrincipal CustomUserDetails user,  NoticeRequestDto.CreateDTO request, List<MultipartFile> file) throws Exception {
        //request 객체를 받아온다
        System.out.println("Notice_create request: " + request);
        return new ResponseEntity<Boolean>(noticeService.create(user.getEmail(), request,file), HttpStatus.OK);
        //new: 생성
    }

    // 공지사항 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails user, NoticeRequestDto.DeleteDTO request) throws Exception {
        System.out.println("Notice_delete request" + request);
        return new ResponseEntity<Boolean>(noticeService.delete(user.getEmail(),request), HttpStatus.OK);
    }

    // 공지사항 수정
    @PutMapping
    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user, NoticeRequestDto.UpdateDTO request, List<MultipartFile> file) throws Exception {
        System.out.println("Notice_update request" + request);
        return new ResponseEntity<Boolean>(noticeService.update(user.getEmail(),request,file), HttpStatus.OK);
    }
}
