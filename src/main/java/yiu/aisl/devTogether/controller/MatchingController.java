package yiu.aisl.devTogether.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.service.MainService;
import yiu.aisl.devTogether.service.MatchingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    //멘토 조회(멘티가 멘토 조회)
    @GetMapping("/mentor")
    public ResponseEntity<Object> mentorList(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return new ResponseEntity<Object>(matchingService.mentorList(customUserDetails), HttpStatus.OK);
    }

    //멘티 조회(멘토가 멘티 조회)
    @GetMapping("/mentee")
    public ResponseEntity<Object> menteeList(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        return new ResponseEntity<Object>(matchingService.menteeList(customUserDetails), HttpStatus.OK);
    }


    //멘토  스크랩
    @PostMapping("/scrap/mentor")
    public ResponseEntity<Boolean> mentorScrap(@AuthenticationPrincipal CustomUserDetails user, MatchingRequestDto.ScrapDto request) throws Exception {
        return new ResponseEntity<Boolean>(matchingService.mentorScrap(user.getEmail(), request), HttpStatus.OK);
    }
    //멘티  스크랩
    @PostMapping("/scrap/mentee")
    public ResponseEntity<Boolean> menteeScrap(@AuthenticationPrincipal CustomUserDetails user, MatchingRequestDto.ScrapDto request) throws Exception {
        return new ResponseEntity<Boolean>(matchingService.menteeScrap(user.getEmail(), request), HttpStatus.OK);
    }


    // 신청하기(멘티가 멘토에게)
    @PostMapping("/matching/application/mentor")
    public ResponseEntity<Boolean> applyMentor(@AuthenticationPrincipal CustomUserDetails user, MatchingRequestDto.MentorApplyDTO request)throws Exception  {
        return new ResponseEntity<Boolean>(matchingService.applyMentor(user.getEmail(),request), HttpStatus.OK);
    }
    // 신청하기(멘토가 멘티에게)
    @PostMapping("/matching/application/mentee")
    public ResponseEntity<Boolean> applyMentee(@AuthenticationPrincipal CustomUserDetails user, MatchingRequestDto.MenteeApplyDTO request)throws Exception  {
        return new ResponseEntity<Boolean>(matchingService.applyMentee(user.getEmail(),request), HttpStatus.OK);
    }


    //신청 수락
    @PutMapping("/matching/application")
    public ResponseEntity<Boolean> approve(@AuthenticationPrincipal CustomUserDetails customUserDetails, MatchingRequestDto.ApproveDTO request) throws Exception {
        return new ResponseEntity<Boolean>(matchingService.approve(customUserDetails,request), HttpStatus.OK);
    }
    //신청 삭제
    @DeleteMapping("/matching/application")
    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, MatchingRequestDto.DeleteDTO request) throws Exception {
        return new ResponseEntity<Boolean>(matchingService.delete(customUserDetails,request), HttpStatus.OK);
    }
    //신청 거절
    @PutMapping("/matching/refusal")
    public ResponseEntity<Boolean> refusal(@AuthenticationPrincipal CustomUserDetails customUserDetails, MatchingRequestDto.RefusalDTO request) throws Exception {
        return new ResponseEntity<Boolean>(matchingService.refusal(customUserDetails,request), HttpStatus.OK);
    }
    //신청 확정
    @PostMapping("/matching/confirmation")
    public ResponseEntity<Boolean> confirm(@AuthenticationPrincipal CustomUserDetails customUserDetails, MatchingRequestDto.ConfirmDTO request)throws Exception  {
        return new ResponseEntity<Boolean>(matchingService.confirm(customUserDetails,request), HttpStatus.OK);
    }

    //신청 종료
    @PostMapping("/matching/end")
    public ResponseEntity<Boolean> end(@AuthenticationPrincipal CustomUserDetails user, MatchingRequestDto.EndDTO request)throws Exception  {
        return new ResponseEntity<Boolean>(matchingService.end(user.getEmail(),request), HttpStatus.OK);
    }














}