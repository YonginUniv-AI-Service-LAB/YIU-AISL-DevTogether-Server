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
import yiu.aisl.devTogether.domain.state.RoleCategory;
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
    @GetMapping("/send/mentor")
    public ResponseEntity<List> getMessageMentor(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reviewService.getSend(user.getEmail(), 1), HttpStatus.OK);
    }

    @GetMapping("/send/mentee")
    public ResponseEntity<List> getMessageMentee(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reviewService.getSend(user.getEmail(), 2), HttpStatus.OK);
    }

    //받은 리뷰 조회
    @GetMapping("/receive/mentor")
    public ResponseEntity<List> creatReportMentor(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reviewService.getReceive(user.getEmail(), 1), HttpStatus.OK);
    }

    @GetMapping("/receive/mentee")
    public ResponseEntity<List> creatReportMentee(@AuthenticationPrincipal CustomUserDetails user) throws Exception {
        return new ResponseEntity<List>(reviewService.getReceive(user.getEmail(), 2), HttpStatus.OK);
    }

    //리뷰 작성
    @PostMapping("/mentor")
    public ResponseEntity<Boolean> creatReportMentor(@AuthenticationPrincipal CustomUserDetails user, ReviewRequestDto.creatDto request) throws Exception {
        System.out.println(user.getEmail());
        System.out.println("서비스 들어가기전 입력  " + request.contents + "  contents: " + request.contents + "  별점 :  " + request.star1 + request.star2 + request.star3);
        return new ResponseEntity<Boolean>(reviewService.creatreview(user.getEmail(), request, 1), HttpStatus.OK);
    }

    @PostMapping("/mentee")
    public ResponseEntity<Boolean> creatReportMentee(@AuthenticationPrincipal CustomUserDetails user, ReviewRequestDto.creatDto request) throws Exception {
        return new ResponseEntity<Boolean>(reviewService.creatreview(user.getEmail(), request, 2), HttpStatus.OK);
    }

    //리뷰 숨기기
    @PostMapping("/hide/mentor")
    public ResponseEntity<Boolean> hideReportMentor(@AuthenticationPrincipal CustomUserDetails user, ReviewRequestDto.hideDto request) throws Exception {
        return new ResponseEntity<Boolean>(reviewService.switchHide(user.getEmail(), request, 1), HttpStatus.OK);
    }

    @PostMapping("/hide/mentee")
    public ResponseEntity<Boolean> hideReportMentee(@AuthenticationPrincipal CustomUserDetails user, ReviewRequestDto.hideDto request) throws Exception {
        return new ResponseEntity<Boolean>(reviewService.switchHide(user.getEmail(), request, 2), HttpStatus.OK);
    }
}
