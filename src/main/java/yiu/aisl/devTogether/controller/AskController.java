package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.dto.AskRequestDto;
import yiu.aisl.devTogether.service.AskService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ask")
public class AskController {
    private final AskService askService;

    // ask 조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception {
        return new ResponseEntity<List>(askService.getList(), HttpStatus.OK);
    }
    // ask 등록
    @PostMapping
    public ResponseEntity<Boolean> create(@ModelAttribute AskRequestDto.CreateDTO request) throws Exception {

        System.out.println("ask_create request: " + request);
        return new ResponseEntity<Boolean>(askService.create(request), HttpStatus.OK);
    }

    // ask 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@ModelAttribute AskRequestDto.DeleteDTO request) throws Exception {
        System.out.println("ask_delete request" + request);
        return new ResponseEntity<Boolean>(askService.delete(request), HttpStatus.OK);
    }

    // ask 수정
    @PutMapping
    public ResponseEntity<Boolean> update(@ModelAttribute AskRequestDto.UpdateDTO request) throws Exception {
        System.out.println("ask_update request" + request);
        return new ResponseEntity<Boolean>(askService.update(request), HttpStatus.OK);
    }
}
