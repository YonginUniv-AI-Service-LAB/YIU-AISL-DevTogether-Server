package yiu.aisl.devTogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.BoardRequestDto;
import yiu.aisl.devTogether.dto.FilesResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.UserProfileRepository;
import yiu.aisl.devTogether.repository.UserRepository;
import yiu.aisl.devTogether.service.BoardService;
import yiu.aisl.devTogether.service.FilesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {
    private final BoardService boardService;

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
//    @PostMapping
//    public ResponseEntity<Boolean> create(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateDto request, List<MultipartFile> file) throws Exception {
//        return new ResponseEntity<Boolean>(boardService.create(user.getEmail(), request, file), HttpStatus.OK);
//    }

    @PostMapping("/mentor")
    public ResponseEntity<Boolean> createForMentor(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateDto request, List<MultipartFile> file) throws Exception {
        return new ResponseEntity<Boolean>(boardService.create(user.getEmail(), 1, request, file), HttpStatus.OK);
    }

    @PostMapping("/mentee")
    public ResponseEntity<Boolean> createForMentee(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateDto request, List<MultipartFile> file) throws Exception {
        return new ResponseEntity<Boolean>(boardService.create(user.getEmail(), 2, request, file), HttpStatus.OK);
    }

    //게시판 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.DeleteDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.delete(user.getEmail(), request), HttpStatus.OK);
    }

    //게시판 수정
//    @PutMapping
//    public ResponseEntity<Boolean> update(@AuthenticationPrincipal CustomUserDetails user, @ModelAttribute BoardRequestDto.UpdateDto request, List<MultipartFile> file) throws Exception {
//        return new ResponseEntity<Boolean>(boardService.update(user.getEmail(), request, file), HttpStatus.OK);
//    }
    @PutMapping("/mentor")
    public ResponseEntity<Boolean> updateMentor(@AuthenticationPrincipal CustomUserDetails user, @ModelAttribute BoardRequestDto.UpdateDto request, List<MultipartFile> file) throws Exception {
        return new ResponseEntity<Boolean>(boardService.update(user.getEmail(), 1, request, file), HttpStatus.OK);
    }

    @PutMapping("/mentee")
    public ResponseEntity<Boolean> updateMentee(@AuthenticationPrincipal CustomUserDetails user, @ModelAttribute BoardRequestDto.UpdateDto request, List<MultipartFile> file) throws Exception {
        return new ResponseEntity<Boolean>(boardService.update(user.getEmail(), 2, request, file), HttpStatus.OK);
    }

    //게시판 좋아요
    @PostMapping("/like/mentor")
    public ResponseEntity<Boolean> likeCountMentor(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.likeDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.likes(user.getEmail(), 1, request), HttpStatus.OK);
    }

    @PostMapping("/like/mentee")
    public ResponseEntity<Boolean> likeCountMentee(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.likeDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.likes(user.getEmail(), 2, request), HttpStatus.OK);
    }

    //게시글 스크렙
    @PostMapping("/scrap/mentor")
    public ResponseEntity<Boolean> scrapMentor(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreatScrapDto request) throws Exception {
        System.out.println(user);
        return new ResponseEntity<Boolean>(boardService.createScrap(user.getEmail(), 1, request), HttpStatus.OK);
    }

    @PostMapping("/scrap/mentee")
    public ResponseEntity<Boolean> scrapMentee(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreatScrapDto request) throws Exception {
        System.out.println(user);
        return new ResponseEntity<Boolean>(boardService.createScrap(user.getEmail(), 2, request), HttpStatus.OK);
    }

    //댓글 등록
    @PostMapping("/comment/mentor")
    public ResponseEntity<Boolean> createCommentMentor(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.createComment(user.getEmail(), request, 1), HttpStatus.OK);
    }

    @PostMapping("/comment/mentee")
    public ResponseEntity<Boolean> createCommentMentee(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.CreateCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.createComment(user.getEmail(), request, 2), HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/comment")
    public ResponseEntity<Boolean> deleteComment(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.DeleteCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.deleteComment(user.getEmail(), request), HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/comment/mentor")
    public ResponseEntity<Boolean> updateCommetMentor(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.UpdateCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.updateComment(user.getEmail(), 1, request), HttpStatus.OK);
    }

    @PutMapping("/comment/mentee")
    public ResponseEntity<Boolean> updateCommetMentee(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.UpdateCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.updateComment(user.getEmail(), 2, request), HttpStatus.OK);
    }

    //댓글 좋아요
    @PostMapping("/comment/like/mentor")
    public ResponseEntity<Boolean> likeCountCommentMentor(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.likeCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.likeComment(user.getEmail(), 1, request), HttpStatus.OK);
    }

    @PostMapping("/comment/like/mentee")
    public ResponseEntity<Boolean> likeCountCommentMentee(@AuthenticationPrincipal CustomUserDetails user, BoardRequestDto.likeCommentDto request) throws Exception {
        return new ResponseEntity<Boolean>(boardService.likeComment(user.getEmail(), 2, request), HttpStatus.OK);
    }
}
