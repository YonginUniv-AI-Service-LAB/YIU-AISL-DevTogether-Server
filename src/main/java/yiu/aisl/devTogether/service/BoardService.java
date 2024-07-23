package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.BoardCategory;
import yiu.aisl.devTogether.domain.state.PushCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.BoardRequestDto;
import yiu.aisl.devTogether.dto.FilesResponseDto;
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
    public final PushRepository pushRepository;
    public final UserProfileRepository userProfileRepository;

    //게시판 전체 조회
    public List<BoardDto> getListAll() throws Exception {
        try {
            System.out.println(filesService);
            List<Board> board = boardRepository.findAll();
            List<BoardDto> getList = new ArrayList<>();
            board.forEach(s -> {
                try {
                    getList.add(BoardDto.getboardDto(s, filesService.downloadProfileFile(1, s.getUserProfile().getUserProfileId()), null));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
//            board.forEach(s -> {
//                try {
//                    System.out.println(filesService.downloadProfileFile(1,s.getUserProfile().getUserProfileId()));
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });
            getList.sort((m1, m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()));
            return getList;
        } catch (Exception e) {
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
            BoardDto response = BoardDto.getboardDto(board, filesService.downloadProfileFile(1, board.getUserProfile().getUserProfileId()), filesService);

            if (board.getFiles()) {
                List<FilesResponseDto> filesList = filesService.getFiles(2, board.getBoardId());
//                System.out.println(filesList);
                response.setFilesList(filesList);
            }

            return response;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //게시판 등록
    public Boolean create(String email, Integer role, BoardRequestDto.CreateDto request, List<MultipartFile> file) throws Exception {
        //403 권한 없음
        User user = findByUserEmail(email);
        UserProfile userProfile = findByUserProfile(user, role);

        //400 데이터 미입력
        if (request.getTitle().isEmpty() || request.getContents().isEmpty() || request.getCategory() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        BoardCategory boardCategory = BoardCategory.fromInt(request.getCategory());
        Boolean files = filesService.isMFile(file);
//        System.out.println(files);
        try {
            Board board = Board.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .userProfile(userProfile)
                    .files(files)
                    .Category(boardCategory)
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
        if (!existingboard.getUserProfile().getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        try {
            boardRepository.deleteById(request.getBoardId());
            if (board.get().getFiles()) {
                filesService.deleteAllFile(2, board.get().getBoardId());
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //게시판 수정 -- 이미지 코드 확인 필요
    public Boolean update(String email, Integer role, BoardRequestDto.UpdateDto request, List<MultipartFile> file) throws Exception {
        User user = findByUserEmail(email);
        UserProfile userProfile = findByUserProfile(user, role);
        //400: 데이터 미입력
        if (request.getTitle().isEmpty() || request.getContents().isEmpty() || request.getCategory() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음
        Optional<Board> board = boardRepository.findByBoardId(request.getBoardId());
        if (board.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        //403: 권한없음
        Board existingboard = board.get();

        if (!existingboard.getUserProfile().equals(userProfile)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        List<Files> filesList = filesRepository.findByTypeAndTypeId(2, request.getBoardId());
        System.out.println(filesList.isEmpty());
        Boolean files = filesService.isMFile(file) || !filesList.isEmpty();
        System.out.println(files);
        BoardCategory boardCategory = BoardCategory.fromInt(request.getCategory());
        try {
            existingboard.setTitle(request.getTitle());
            existingboard.setContents(request.getContents());
            existingboard.setCategory(boardCategory);
            existingboard.setFiles(files);
            boardRepository.save(existingboard);
            //파일 관련 코드
            //파일이 있으면
            if (files) {
                filesService.filesMUpdate(2, board.get().getBoardId(), file, request.getDeleteId());
                //보드에 있는 파일 전부 삭제
//                filesService.deleteAllFile(2, board.get().getBoardId());
//
//                //파일 일괄 생성
//                filesService.saveFileMDb(file, 2, request.getBoardId());
            }
            filesList = filesRepository.findByTypeAndTypeId(2, request.getBoardId());
            files = !filesList.isEmpty();
            existingboard.setFiles(files);
            boardRepository.save(existingboard);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //게시글 좋아요
    public boolean likes(String email, Integer type, BoardRequestDto.likeDto request) throws Exception {
        try {
            // 403: 권한 없음
            User user = findByUserEmail(email);
            UserProfile userProfile = findByUserProfile(user, type);
            // 404: id 없음
            Board board = findByBoardId(request.getBoardId());
            // 1 값 들어올 때 좋아요 누를때 // 0 좋아요 취소
            if (request.getCount()) {
                if (likeRepository.findByUseridAndTypeId(userProfile, request.getBoardId()).isPresent()) {
                    throw new CustomException(ErrorCode.DUPLICATE);
                } else {//만약 없으면 좋아요 db 만들기
                    Likes makelike = Likes.builder()
                            .userid(userProfile)
                            .typeId(request.getBoardId())
                            .type(0)
                            .build();
                    likeRepository.save(makelike);
                    Push push = Push.builder()
                            .user(board.getUserProfile().getUser())
                            .type(PushCategory.게시판)
                            .typeId(board.getBoardId())
                            .contents(userProfile.getNickname() + "님이 게시글에 좋아요를 남겼습니다")
                            .checks(1)
                            .build();
                    pushRepository.save(push);

                }
            } else {// 취소 했을때
                if (likeRepository.findByUseridAndTypeId(userProfile, request.getBoardId()).isPresent()) {
                    Likes likes = findByLikesId(userProfile, request.getBoardId());
                    likeRepository.delete(likes);
                } else {//만약 없으면 오류
                    throw new Exception();
                }
            }
            return true;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //게시글 스크랩
    public Boolean createScrap(String email, Integer type, BoardRequestDto.CreatScrapDto request) throws Exception {
        try {
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
            UserProfile userProfile = findByUserProfile(user, type);
            if (boardScrapRepository.findByUserAndBoard(userProfile, board).isPresent()) {
                boardScrapRepository.deleteByUserAndBoard(userProfile, board);
                return true;
            } else {
                BoardScrap scrap = BoardScrap.builder()
                        .user(userProfile)
                        .board(board)
                        .build();
                boardScrapRepository.save(scrap);
                return true;

            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 등록
    public Boolean createComment(String email, BoardRequestDto.CreateCommentDto request, Integer role) throws Exception {
        try {
            //400: 데이터 미입력
            if (request.getContents() == null) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }
            //403: 권한없음
            User user = findByUserEmail(email);
            UserProfile userProfile = findByUserProfile(user, role);
            //404: 보드 id 없음
            Board board = findByBoardId(request.getBoardId());
            Comment comment = Comment.builder()
                    .board(board)
                    .contents(request.getContents())
                    .userProfile(userProfile)
                    .build();

            commentRepository.save(comment);
            Push push = Push.builder()
                    .user(board.getUserProfile().getUser())
                    .type(PushCategory.댓글)
                    .typeId(board.getBoardId())
                    .contents(userProfile.getNickname() + "님이 게시글에 댓글을 남겼습니다")
                    .checks(1)
                    .build();
            pushRepository.save(push);
            return true;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 삭제
    public Boolean deleteComment(String email, BoardRequestDto.DeleteCommentDto request) throws Exception {
        try {
            //400: 데이터 미입력
            if (request.getCommentId() == null) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }
            //404: id 없음"
            Optional<Comment> comment = commentRepository.findByCommentId(request.getCommentId());
            if (comment.isEmpty()) {
                throw new CustomException(ErrorCode.NOT_EXIST_ID);
            }
            Optional<Board> board = boardRepository.findByBoardId(comment.get().getBoard().getBoardId());
            if (board.isEmpty()) {
                throw new CustomException(ErrorCode.NOT_EXIST_ID);
            }
//        Board board = findByBoardId(request.getBoardId());

            //403: 권한없음 -- 다시 확인하기
//        Board existingboard = board.get();
            Comment existingComment = comment.get();
            if (!existingComment.getUserProfile().getUser().getEmail().equals(email)) {
                throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
            }
            commentRepository.deleteById(request.getCommentId());
            return true;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //댓글 수정
    public Boolean updateComment(String email, Integer type, BoardRequestDto.UpdateCommentDto request) throws Exception {
        try {
            User user = findByUserEmail(email);
            UserProfile userProfile = findByUserProfile(user, type);
            //400: 데이터 미입력
            if (request.getCommentId() == null || request.getContents() == null) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }

            //404: id 없음
            Optional<Comment> comment = commentRepository.findByCommentId(request.getCommentId());
            Optional<Board> board = boardRepository.findByBoardId(comment.get().getBoard().getBoardId());

            if (board.isEmpty() || comment.isEmpty()) {
                throw new CustomException(ErrorCode.NOT_EXIST_ID);
            }
            //403: 권한없음
            Comment existingboard = comment.get();
            if (!existingboard.getUserProfile().getUser().equals(user)) {
                throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
            }

            existingboard.setContents(request.getContents());
            commentRepository.save(existingboard);
            return true;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //댓글 좋아요
    public Boolean likeComment(String email, Integer type, BoardRequestDto.likeCommentDto request) throws Exception {
        try {
            // 403: 권한 없음
            User user = findByUserEmail(email);
            UserProfile userProfile = findByUserProfile(user, type);
            // 404: id 없음

            Comment comment = findByCommentId(request.getCommentId());


            // 1 값 들어올 때 좋아요 누를때 // 0 좋아요 취소
            if (request.getCount()) {
                if (likeRepository.findByUseridAndTypeId(userProfile, request.getCommentId()).isPresent()) {
                    throw new CustomException(ErrorCode.DUPLICATE);
                } else {//만약 없으면 좋아요 db 만들기
                    Likes makelike = Likes.builder()
                            .userid(userProfile)
                            .typeId(request.getCommentId())
                            .type(1)
                            .build();
                    likeRepository.save(makelike);
                    Push push = Push.builder()
                            .user(comment.getUserProfile().getUser())
                            .type(PushCategory.댓글)
                            .typeId(comment.getCommentId())
                            .contents(userProfile.getNickname() + "님이 댓글에 좋아요를 남겼습니다")
                            .checks(1)
                            .build();
                    pushRepository.save(push);
                }
            } else {// 취소 했을때
                if (likeRepository.findByUseridAndTypeId(userProfile, request.getCommentId()).isPresent()) {
                    Likes likes = findByLikesId(userProfile, request.getCommentId());
                    likeRepository.delete(likes);
                } else {//만약 없으면 좋아요 db 만들기
                    throw new CustomException(ErrorCode.DUPLICATE);
                }
            }
            return true;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public Likes findByLikesId(UserProfile user, Long typeId) {
        return likeRepository.findByUseridAndTypeId(user, typeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public Board findByBoardId(Long boardId) {
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public Comment findByCommentId(Long commentId) {
        return commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public UserProfile findByUserProfile(User user, Integer role) {
        return userProfileRepository.findByUserAndRole(user, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

}
