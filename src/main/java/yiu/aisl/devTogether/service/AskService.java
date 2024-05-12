package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import yiu.aisl.devTogether.dto.AskRequestDto;
import yiu.aisl.devTogether.dto.AskResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.AskRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AskService {
    private final AskRepository askRepository;
    private final UserRepository userRepository;

    //ask 조회
    public List <AskResponseDto >getList() throws  Exception {
        List<Ask> ask = askRepository.findByOrderByCreatedAtDesc();
        List<AskResponseDto> getListDto = new ArrayList<>();
        ask.forEach(s->getListDto.add(AskResponseDto.GetAskDTO(s)));
        return getListDto;

    }




    //ask 등록
    public Boolean create(AskRequestDto.CreateDTO request) {

        AskCategory askCategory = AskCategory.fromInt(request.getAskCategory());
        RoleCategory role = RoleCategory.fromInt(request.getRole());
        //400 - 데이터 미입력
        if(request.getTitle() == null || request.getContents() == null || request.getAskCategory() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try{

            Ask ask = Ask.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .status(StatusCategory.APPLICATION) // 신청
                    .file(request.getFile())
                    .askCategory(askCategory)
                    .build();


            askRepository.save(ask);
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;



    }

    public void answerAsk(Long askId, String answer) {
        Ask ask = askRepository.findById(askId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));

        ask.setAnswer(answer);
        ask.setStatus(StatusCategory.COMPLETION);
        askRepository.save(ask);
    }


    //ask 삭제
    public Boolean delete(AskRequestDto.DeleteDTO request) {
        //404 - id없음
        Ask ask = findByAskId(request.getAskId());
        if(ask == null){
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        try{
            askRepository.deleteById(request.getAskId());
            return true;
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }




    //ask 수정
    public Boolean update(AskRequestDto.UpdateDTO request) {
        //400 - 데이터 미입력
        if(request.getTitle() == null || request.getContents() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        }
        return true;
    }


    private Ask findByAskId(Long askId) {
        return askRepository.findById(askId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }




}
