package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.ReportRequestDto;
import yiu.aisl.devTogether.service.ReportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    public final ReportService reportService;

    //신고하기
    @PostMapping
    public ResponseEntity<Boolean> creatReport(@AuthenticationPrincipal CustomUserDetails user, ReportRequestDto.creatDto request) throws Exception {
        return new ResponseEntity<Boolean>(reportService.creatReport(user.getEmail(), request), HttpStatus.OK);
    }

    //신고목록 가져오기 관리자만
    @GetMapping
    public ResponseEntity<List> getList(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reportService.getList(user.getEmail()), HttpStatus.OK);
    }

    //신고 처리하기 관리자만
    @PutMapping
    public ResponseEntity<Boolean> treat(@AuthenticationPrincipal CustomUserDetails user, ReportRequestDto.treatDto request) throws Exception {
        return new ResponseEntity<Boolean>(reportService.treat(user.getEmail(), request), HttpStatus.OK);
    }
}
