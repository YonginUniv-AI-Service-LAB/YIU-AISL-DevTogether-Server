package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.BoardRequestDto;
import yiu.aisl.devTogether.dto.FilesResponseDto;
import yiu.aisl.devTogether.service.BoardService;
import yiu.aisl.devTogether.service.FilesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {
    private final BoardService boardService;
    private final FilesService filesService;

    //파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<FilesResponseDto> downloadFile(Long fileId) throws Exception {
        return new ResponseEntity<FilesResponseDto>(filesService.downloadFile(fileId), HttpStatus.OK);
    }

    //게시판 전체조회
    @GetMapping
    public ResponseEntity<List> getList() throws Exception {
        return new ResponseEntity<List>(boardService.getListAll(), HttpStatus.OK);
    }

    //게시판 부분 조회
    @GetMapping("/post")
    public ResponseEntity<BoardDto> getListOne(BoardRequestDto.DetailDto request) throws Exception {
        return new ResponseEntity<BoardDto>(boardService.getDetail(request), HttpStatus.OK);
    }

    //게시판 등록
    @PostMapping
    public ResponseEntity<Boolean> create(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateDto request, List<MultipartFile> file) throws Exception {
        return new ResponseEntity<Boolean>(boardService.create(user.getEmail(), request, file), HttpStatus.OK);
    }

    //게시판 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.DeleteDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.delete(user.getEmail(), request), HttpStatus.OK);
    }

    //게시판 수정 ~~~~~~~~~~~  file 확인 필요
    @PutMapping
    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user, @ModelAttribute BoardRequestDto.UpdateDto request, List<MultipartFile> file) throws Exception {
        return new ResponseEntity<Boolean>(boardService.update(user.getEmail(), request, file), HttpStatus.OK);
    }

    //게시판 좋아요
    @PostMapping("/like")
    public ResponseEntity<Boolean> likeCount(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.likeDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.likes(user.getEmail(), request), HttpStatus.OK);
    }

    //게시글 스크렙
    @PostMapping("/scrap")
    public ResponseEntity<Boolean> scrap(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreatScrapDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.createScrap(user.getEmail(), request), HttpStatus.OK);
    }

    //댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<Boolean> createComment(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.createComment(user.getEmail(), request), HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/comment")
    public ResponseEntity<Boolean> deleteComment(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.DeleteCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.deleteComment(user.getEmail(), request), HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/comment")
    public ResponseEntity<Boolean> updateCommet(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.UpdateCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.updateComment(user.getEmail(), request), HttpStatus.OK);
    }

    //댓글 좋아요
    @PostMapping("/Comment/like")
    public ResponseEntity<Boolean> likeCountComment(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.likeCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.likeComment(user.getEmail(), request), HttpStatus.OK);
    }
}
