package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.BoardRequestDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final FilesService filesService;
    private final FilesRepository filesRepository;
    private final BoardScrapRepository boardScrapRepository;

    //게시판 전체 조회
    public List<BoardDto> getListAll() throws Exception {
        try {
            List<Board> board = boardRepository.findAll();
            List<BoardDto> getList = new ArrayList<>();
            board.forEach(s -> getList.add(BoardDto.getboardDto(s)));
            return getList;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //게시판 부분 조회
    public BoardDto getDetail(BoardRequestDto.DetailDto request) throws Exception {
        //404 id 없음
        Board board = boardRepository.findByBoardId(request.getBoardId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        });

        try {
            BoardDto response = BoardDto.getboardDto(board);

            if (board.getFiles()) {
                List<Files> filesList = filesService.getFiles(2, board.getBoardId());
                System.out.println(filesList);
                response.setFilesList(filesList);
            }

            return response;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //게시판 등록
    public Boolean create(String email, BoardRequestDto.CreateDto request, List<MultipartFile> file) {
        //403 권한 없음 - db 유무만 탐색
        User user = findByUserEmail(email);

        //400 데이터 미입력
        if (request.getTitle().isEmpty() || request.getContents().isEmpty()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        Boolean files = filesService.isMFile(file);
//        System.out.println(files);
        try {
            Board board = Board.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .user(user)
                    .files(files)
                    .build();
            boardRepository.save(board);
            if (files) {
                filesService.saveFileMDb(file, 2, board.getBoardId());
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    //게시판 삭제
    public Boolean delete(String email, BoardRequestDto.DeleteDto request) throws Exception {
        //400: 데이터 미입력
        if (request.getBoardId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음"
        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
        if (board.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        //403: 권한없음 ????
        Board existingboard = board.get();
        if (!existingboard.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        try {
            boardRepository.deleteById(request.getBoardId());
            if (board.get().getFiles()) {
                filesService.deleteFile(2, board.get().getBoardId());
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //게시판 수정 -- 이미지 코드 확인 필요
    public Boolean update(String email, BoardRequestDto.UpdateDto request) throws Exception {
        User user = findByUserEmail(email);

        //400: 데이터 미입력
        if (request.getTitle().isEmpty() || request.getContents().isEmpty()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        //404: id 없음
        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
        if (board.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        //403: 권한없음
        Board existingboard = board.get();
        if (!existingboard.getUser().equals(user)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        try {
            existingboard.setTitle(request.getTitle());
            existingboard.setContents(request.getContents());

            boardRepository.save(existingboard);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //게시글 좋아요
    public boolean likes(String email, BoardRequestDto.likeDto request) throws Exception {
        // 403: 권한 없음
        User user = findByUserEmail(email);
        // 404: id 없음
        findByBoardId(request.getBoardId());
        try {
            // 1 값 들어올 때 좋아요 누를때 // 0 좋아요 취소
            if(request.getCount()){
                if (likeRepository.findByUseridAndTypeId(user, request.getBoardId()).isPresent()) {
                    throw new CustomException(ErrorCode.DUPLICATE);
                } else {//만약 없으면 좋아요 db 만들기
                    Likes makelike = Likes.builder()
                            .userid(user)
                            .typeId(request.getBoardId())
                            .type(0)
                            .build();
                    likeRepository.save(makelike);
                }
            }else {// 취소 했을때
                if (likeRepository.findByUseridAndTypeId(user, request.getBoardId()).isPresent()) {
                    Likes likes = findByLikesId(user, request.getBoardId());
                    likeRepository.delete(likes);
                } else {//만약 없으면 오류
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //게시글 스크랩
    public Boolean createScrap(String email, BoardRequestDto.CreatScrapDto request) throws Exception {
        //400: 데이터 미입력
        if (request.getBoardId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음"
//        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
//        if (board.isEmpty()) {
//            throw new CustomException(ErrorCode.NOT_EXIST_ID);
//        }
        Board board = findByBoardId(request.getBoardId());



        //403: 권한없음
       User user = findByUserEmail(email);

        if(boardScrapRepository.findByUserAndBoard(user, board).isPresent()) {
            boardScrapRepository.deleteByUserAndBoard(user, board);
            return true;
        } else {
            try {
                BoardScrap scrap = BoardScrap.builder()
                        .user(user)
                        .board(board)
                        .build();
                boardScrapRepository.save(scrap);
                return true;
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }

    //댓글 등록
    public Boolean createComment(String email, BoardRequestDto.CreateCommentDto request) throws Exception {

        //400: 데이터 미입력
        if (request.getContents() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //403: 권한없음
        User user = findByUserEmail(email);

        try {
            Board board = findByBoardId(request.getBoardId());

            Comment comment = Comment.builder()
                    .board(board)
                    .contents(request.getContents())
                    .user(user)
                    .build();

            commentRepository.save(comment);

            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //댓글 삭제
    public Boolean deleteComment(String email, BoardRequestDto.DeleteCommentDto request) throws Exception {
        //400: 데이터 미입력
        if (request.getBoardId() == null || request.getCommentId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음"
        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
        if (board.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
//        Board board = findByBoardId(request.getBoardId());
        Optional<Comment> comment = commentRepository.findByCommentId(request.getCommentId());
        if (comment.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        //403: 권한없음 -- 다시 확인하기
//        Board existingboard = board.get();
        Comment existingComment = comment.get();
        if (!existingComment.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        try {
            commentRepository.deleteById(request.getCommentId());
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //댓글 수정
    public Boolean updateComment(String email, BoardRequestDto.UpdateCommentDto request) throws Exception {
        User user = findByUserEmail(email);
        //400: 데이터 미입력
        if (request.getBoardId() == null || request.getCommentId() == null || request.getContents() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음
        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
        Optional<Comment> comment = commentRepository.findByCommentId(request.getCommentId());
        if (board.isEmpty() || comment.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        //403: 권한없음
        Comment existingboard = comment.get();
        if (!existingboard.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        try {
            existingboard.setContents(request.getContents());
            commentRepository.save(existingboard);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
    //댓글 좋아요
    public Boolean likeComment(String email, BoardRequestDto.likeCommentDto request) throws Exception{
        // 403: 권한 없음
        User user = findByUserEmail(email);
        // 404: id 없음
        Comment comment = commentRepository.findByCommentId(request.getCommentId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
        try {
            // 1 값 들어올 때 좋아요 누를때 // 0 좋아요 취소
            if(request.getCount()){
                if (likeRepository.findByUseridAndTypeId(user, request.getCommentId()).isPresent()) {
                    throw new CustomException(ErrorCode.DUPLICATE);
                } else {//만약 없으면 좋아요 db 만들기
                    Likes makelike = Likes.builder()
                            .userid(user)
                            .typeId(request.getCommentId())
                            .type(1)
                            .build();
                    likeRepository.save(makelike);
                }
            }else {// 취소 했을때
                if (likeRepository.findByUseridAndTypeId(user, request.getCommentId()).isPresent()) {
                    Likes likes = findByLikesId(user, request.getCommentId());
                    likeRepository.delete(likes);
                } else {//만약 없으면 좋아요 db 만들기
                    throw new CustomException(ErrorCode.DUPLICATE);
                }
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    public Likes findByLikesId(User user, Long typeId){
        return likeRepository.findByUseridAndTypeId(user, typeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public Board findByBoardId(Long boardId) {
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

}
