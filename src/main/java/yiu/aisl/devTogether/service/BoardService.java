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

    //게시판 전체 조회
    public List<BoardDto> getListAll() throws Exception {
        List<Board> board = boardRepository.findAll();
        List<BoardDto> getList = new ArrayList<>();
        board.forEach(s -> getList.add(BoardDto.getboardDto(s)));
        return getList;
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
    public Boolean create(String email, BoardRequestDto.CreateDto request, MultipartFile file) {
        //403 권한 없음 - db 유무만 탐색
        User user = findByUserEmail(email);

        //400 데이터 미입력
        if (request.getTitle() == null || request.getContents() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        Boolean files = filesService.isFile(file);

        try {
            Board board = Board.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .user(user)
                    .files(files)
                    .build();
            boardRepository.save(board);
            if (files) {
                filesService.saveFileDb(file, 2, board.getBoardId());
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    //게시판 삭제 -- 저장된 파일도 삭제 하기
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
           if (board.get().getFiles()){
               filesService.deleteFile(2,board.get().getBoardId());
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
        if (request.getTitle() == null || request.getContents() == null) {
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

    //게시글 좋아요 ---test 안함
    public boolean likeCount(String mail, BoardRequestDto.likeDto request) throws Exception {
        // 403: 권한 없음

        // 404: id 없음
        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
        if (board.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        User user = findByUserEmail(mail);
        Optional<Likes> like = likeRepository.findByUseridEmail(user);
        try {
            //좋아요 db 에서 해당 보드/댓글 아이디에 유저가 db에 저장 되어있는지 찾기 ---- 타입 구분 추가 필요
            if (likeRepository.findByUseridEmail(user).isPresent()) {
                Boolean count = request.getCount();
                Likes getlike = like.get();
                if (getlike.getCount())
                    getlike.setCount(true);
                else
                    getlike.setCount(false);
            } else {//만약 없으면 좋아요 db 만들기
                Likes makelike = Likes.builder()
                        .count(true)
                        .typeId(Math.toIntExact(request.getBoardId()))
                        .type(0)
                        .build();
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

//
//    //게시글 스크렙
//    public Boolean createScrap(String user, BoardRequestDto.CreatScrapDto request) throws Exception{
//        try {
//
//
//            return true;
//        }catch (Exception e){
//            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }

    //댓글 등록
    public Boolean createComment(String email, BoardRequestDto.CreateCommentDto request, Long boardId) throws Exception {

        //400: 데이터 미입력
        if (request.getContents() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //403: 권한없음


        try {
            Board board = findByBoardId(boardId);

            Comment comment = Comment.builder()
                    .board(board)
                    .contents(request.getContents())
                    .build();

            commentRepository.save(comment);

            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //댓글 삭제
    public Boolean deleteComment(String user, BoardRequestDto.DeleteCommentDto request) throws Exception {
        //400: 데이터 미입력
        if (request.getBoardId() == null) {
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
        Board existingboard = board.get();
        Comment existingComment = comment.get();
        if (!existingboard.getUser().equals(user)) {
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

    //댓글 좋아요


    public Board findByBoardId(Long boardId) {
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByUserEmail(String studentId) {
        return userRepository.findByEmail(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

}
