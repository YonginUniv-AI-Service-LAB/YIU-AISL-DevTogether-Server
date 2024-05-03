package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.BoardRequestDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    //게시판 등록 - 이미지 관련 넣기
    public Boolean create(BoardRequestDto.CreateDto request) {
        //403 권한 없음

        //400 데이터 미입력
        if (request.getTitle() == null || request.getContents() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            Board board = Board.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    //file 추가 없음
                    .build();

            boardRepository.save(board);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

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
        Board board = boardRepository.findByBoardId(request.getBoardID()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_EXIST);
        });
        try {
            BoardDto response = BoardDto.getboardDto(board);
            return response;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //게시판 삭제 -- 저장된 파일도 삭제 하기
    public Boolean delete(BoardRequestDto.DeleteDto request) throws Exception {
        //400: 데이터 미입력
        if(request.getBoardId() ==null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        //403: 권한없음 ????

        //404: id 없음"
        Optional<Board>  board = boardRepository.findByBoardId(request.getBoardId());
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST);
        }
        try {

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
    //게시판 수정
    public Boolean update(BoardRequestDto.UpdateDto request) throws Exception{

        //400: 데이터 미입력
        if (request.getTitle() == null || request.getContents() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //403: 권한없음

        //404: id 없음
        Optional<Board>  board = boardRepository.findByBoardId(request.getBoardId());
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST);
        }

        try {




            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    public Board findByBoardId(Long boardId) {
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST));
    }

    //게시글 좋아요
    public boolean likeCount(BoardRequestDto.likeDto request) throws Exception{
        // 403: 권한 없음

        // 404: id 없음
        Optional<Board>  board = boardRepository.findByBoardId(request.getBoardId());
        if(board.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST);
        }
        try {


            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
    //게시글 스크렙

    //댓글 등록

    //댓글 삭제

    //댓글 수정

    //댓글 좋아요
}
