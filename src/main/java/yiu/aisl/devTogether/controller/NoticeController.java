package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.dto.NoticeRequestDto;
import yiu.aisl.devTogether.dto.PwdChangeRequestDto;
import yiu.aisl.devTogether.dto.RegisterDto;
import yiu.aisl.devTogether.service.NoticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notice")
public class NoticeController {
    private final NoticeService noticeService;


    //공지사항 전체조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception{
        return new ResponseEntity<List>(noticeService.getList(), HttpStatus.OK);
    }
    //공지사항 등록
    @PostMapping
    public ResponseEntity<Boolean> create(NoticeRequestDto.CreateDTO request) throws  Exception{
        System.out.println("create request" + request);
        return new ResponseEntity<Boolean>(noticeService.create(request), HttpStatus.OK);
    }
    //공지사항 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(NoticeRequestDto.DeleteDTO request) throws  Exception{

        System.out.println("delete request" + request);
        return new ResponseEntity<Boolean>(noticeService.delete(request), HttpStatus.OK);
    }
    //공지사항 수정
    @PutMapping
    public ResponseEntity<Boolean> update( NoticeRequestDto.UpdateDTO request) throws  Exception{
        System.out.println("update request" + request);
        return new ResponseEntity<Boolean>(noticeService.update(request), HttpStatus.OK);
    }
}
