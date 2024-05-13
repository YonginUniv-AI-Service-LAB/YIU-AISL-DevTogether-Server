package yiu.aisl.devTogether.controller;

import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.BoardRequestDto;
import yiu.aisl.devTogether.service.BoardService;
import yiu.aisl.devTogether.service.ImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {
    private final BoardService boardService;
    private final ImageService imageService;
    //게시판 전체조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception{
        return  new ResponseEntity<List>(boardService.getListAll(), HttpStatus.OK);
    }

    //게시판 부분 조회
    @GetMapping("/post")
    public ResponseEntity<BoardDto> getListOne(BoardRequestDto.DetailDto request) throws  Exception{
        return new ResponseEntity<BoardDto>(boardService.getDetail(request),HttpStatus.OK);
    }

    //게시판 등록

    @PostMapping
    public ResponseEntity<Boolean> create(BoardRequestDto.CreateDto request) throws Exception{
        return new ResponseEntity<Boolean>(boardService.create(request),HttpStatus.OK);
    }
//
//    //게시판 삭제
//    @DeleteMapping
//    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.DeleteDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.delete(user.getEmail() ,request),HttpStatus.OK);
//    }
//
//    //게시판 수정 ~~~~~~~~~~~  user 확인 필요
//    @PutMapping
//    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user, @ModelAttribute BoardRequestDto.UpdateDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.update(user.getEmail(), request), HttpStatus.OK);
//    }
//
//    //게시판 좋아요
//    @PostMapping("/like")
//    public ResponseEntity<Boolean> likeCount(@AuthenticationPrincipal CustomUserDetails user ,BoardRequestDto.likeDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.likeCount(user.getEmail(), request), HttpStatus.OK);
//    }
//
//    //게시글 스크렙
//    @PostMapping("/scrap")
//    public ResponseEntity<Boolean> scrap(BoardRequestDto.scrapDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.scrap(request), HttpStatus.OK);
//    }
//
    //댓글 등록
//    @PostMapping("/comment")
//    public ResponseEntity<Boolean> createComment(@AuthenticationPrincipal CustomUserDetails user ,BoardRequestDto.CreateCommentDto request, Long boardId) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.createComment(user.getEmail(), request, boardId),HttpStatus.OK);
//    }
//    //댓글 삭제
//    @DeleteMapping("/comment")
//    public ResponseEntity<Boolean> deleteComment(BoardRequestDto.DeleteCommentDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.deleteComment(request),HttpStatus.OK);
//    }
//    //댓글 수정
//    @PutMapping("/comment")
//    public ResponseEntity<Boolean> updateCommet(BoardRequestDto.UpdateCommentDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.updateComment(request), HttpStatus.OK);
//    }
//    //댓글 좋아요
//    @PostMapping("/Comment/like")
//    public ResponseEntity<Boolean> likeCountComment(BoardRequestDto.likeCommentDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.likeCountComment(request), HttpStatus.OK);
//    }
}
