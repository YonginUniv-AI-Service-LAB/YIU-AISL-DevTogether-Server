package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.Files;
import yiu.aisl.devTogether.domain.Push;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.*;
import yiu.aisl.devTogether.dto.AskRequestDto;
import yiu.aisl.devTogether.dto.AskResponseDto;
import yiu.aisl.devTogether.dto.FilesResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.AskRepository;
import yiu.aisl.devTogether.repository.FilesRepository;
import yiu.aisl.devTogether.repository.PushRepository;
import yiu.aisl.devTogether.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Transactional
public class AskService {
    private final AskRepository askRepository;
    private final UserRepository userRepository;
    private final FilesService filesService;
    private final PushRepository pushRepository;
    private final FilesRepository filesRepository;

    //ask 조회(내가 쓴 글 조회)
    public List<AskResponseDto> getList(String email) throws Exception {
        User user = findByEmail(email);
        List<Ask> askList = askRepository.findByUserOrderByCreatedAtDesc(user);
        List<AskResponseDto> getList = new ArrayList<>();
        askList.forEach(ask -> getList.add(AskResponseDto.GetAskDTO(ask)));
        for (AskResponseDto ask : getList) {
            List<FilesResponseDto> filesList = new ArrayList<>();
            if (ask.getFiles()) {
                filesList = filesService.getFiles(5, ask.getAskId());
                ask.setFilesList(filesList);
            }
        }
        return getList;
    }
    //ask 조회(관리자용)
    public List <AskResponseDto >getAdminList() throws Exception {
        List<Ask> askAdminList = askRepository.findByOrderByCreatedAtDesc();
        List<AskResponseDto> getAdminList = new ArrayList<>();
        askAdminList.forEach(s->getAdminList.add(AskResponseDto.GetAskDTO(s)));
        for (AskResponseDto ask : getAdminList) {
            List<FilesResponseDto> filesList = new ArrayList<>();
            if (ask.getFiles()) {
                filesList = filesService.getFiles(5, ask.getAskId());
                ask.setFilesList(filesList);
            }
        }
        return getAdminList;

    }



    //ask 사용자 등록
    public Boolean create(String email, AskRequestDto.CreateDTO request, List<MultipartFile> file) throws Exception{
       //404 - 회원없음
        User user = findByEmail(email);
        AskCategory askCategory = AskCategory.fromInt(request.getAskCategory());
        StatusCategory status = StatusCategory.신청;
        Boolean files = filesService.isMFile(file);
        // 400 - 데이터미입력
        if (request.getTitle().isEmpty()|| request.getContents().isEmpty() || request.getAskCategory() == null)
        {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {

            Ask ask = Ask.builder()
                    .user(user)
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .status(status) // 신청
                    .files(files)
                    .askCategory(askCategory)
                    .build();
            askRepository.save(ask);
            if (files) {
                filesService.saveFileMDb(file, 5, ask.getAskId());
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }


    }



    //ask 관리자 답변
    public Boolean answer(String email, Long askId, AskRequestDto.AnswerDTO request) throws Exception{
        // 404 - ID 없음
        Ask ask = findByAskId(askId);

        //400 - 데이터 미입력
        if(request.getAskId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        StatusCategory status = StatusCategory.완료;
        try {
            ask.setAnswer(request.getAnswer());
            ask.setStatus(status);

            Push push = Push.builder()
                    .type(PushCategory.문의)
                    .contents("관리자님이 답변을 남겼습니다.")
                    .user(ask.getUser())
                    .typeId(ask.getAskId())
                    .checks(0)
                    .build();
            askRepository.save(ask);
            pushRepository.save(push);
            return true;
        } catch (Exception e) {
            // 기타 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }







    //ask 삭제
    public Boolean delete(String email, AskRequestDto.DeleteDTO request) throws Exception{
        // 404 - 회원없음
        User user = findByEmail(email);
        //404 - id없음
        Ask ask = findByAskId(request.getAskId());

        //400- 데이터 미입력
        if(request.getAskId()== null){
            throw  new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        //403 - 권한 없음  >> 자기가 쓴 글이 아닌경우
        if (!ask.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        try{
            askRepository.deleteById(request.getAskId());
            if (ask.getFiles()) {
                filesService.deleteAllFile(5, ask.getAskId());
            }
            return true;
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }




    //ask 수정
    public Boolean update(String email, AskRequestDto.UpdateDTO request, List<MultipartFile> file)throws Exception {
        //404- 회원없음
        User user = findByEmail(email);
        AskCategory askCategory = AskCategory.fromInt(request.getAskCategory());
        StatusCategory status = StatusCategory.신청;
        List<Files> filesList = filesRepository.findByTypeAndTypeId(5, request.getAskId());
        Boolean files = filesService.isMFile(file)|| !filesList.isEmpty();
        //404 - id없음
        Ask ask = findByAskId(request.getAskId());

        //400 - 데이터 미입력
        if(request.getAskId() == null || request.getTitle().isEmpty() || request.getContents().isEmpty() || request.getAskCategory() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        }
        //404 - 수정 불가  >> 관리자가 답변을 달아 완료가 된 상태인데 수정하려는 경우
        if(ask.getStatus() == StatusCategory.완료){
            throw new CustomException(ErrorCode.NOT_MODIFICATION);
        }

        //403 - 권한 없음  >> 자기가 쓴 글이 아닌경우
        if (!ask.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try{

            Optional<Ask> modifyAsk = askRepository.findByAskId(request.getAskId());
            modifyAsk.get().setTitle(request.getTitle());
            modifyAsk.get().setContents(request.getContents());
            modifyAsk.get().setStatus(status);
            modifyAsk.get().setFiles(files);
            modifyAsk.get().setAskCategory(askCategory);
            askRepository.save(ask);
            if (files) {
                filesService.filesMUpdate(5, modifyAsk.get().getAskId(), file, request.getDeleteId());
            }
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    public  Ask findByAskId(Long askId) {
        return askRepository.findByAskId(askId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }


}
