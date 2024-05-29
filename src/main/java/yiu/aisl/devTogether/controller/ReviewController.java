package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PostExchange;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.Review;
import yiu.aisl.devTogether.dto.ReportRequestDto;
import yiu.aisl.devTogether.dto.ReviewRequestDto;
import yiu.aisl.devTogether.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    public final ReviewService reviewService;

    //보낸 리뷰 조회
    @GetMapping("/send")
    public ResponseEntity<List> getMessage(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reviewService.getSend(user.getEmail()), HttpStatus.OK);
    }

    //받은 리뷰 조회
    @GetMapping("/receive")
    public ResponseEntity<List> creatReport(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reviewService.getReceive(user.getEmail()), HttpStatus.OK);
    }

    //리뷰 작성
    @PostMapping
    public ResponseEntity<Boolean> creatReport(@AuthenticationPrincipal CustomUserDetails user, ReviewRequestDto.creatDto request) throws Exception {
        return new ResponseEntity<Boolean>(reviewService.creatreview(user.getEmail(), request), HttpStatus.OK);
    }

    //리뷰 숨기기
    @PostMapping("hide")
    public ResponseEntity<Boolean> creatReport(@AuthenticationPrincipal CustomUserDetails user, ReviewRequestDto.hideDto request) throws Exception {
        return new ResponseEntity<Boolean>(reviewService.switchHide(user.getEmail(), request), HttpStatus.OK);
    }
}
