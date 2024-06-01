package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Faq;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.dto.FaqRequestDto;
import yiu.aisl.devTogether.dto.FaqResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.FaqRepository;
import yiu.aisl.devTogether.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FaqService {
    private final FaqRepository faqRepository;
    private  final UserRepository userRepository;
    //faq 조회
    public List <FaqResponseDto> getList() throws Exception{
        List<Faq> faq = faqRepository.findByOrderByCreatedAtDesc();  //생성 일자를 기준으로 내림차순으로 정렬
        List<FaqResponseDto> getListDto = new ArrayList<>();
        faq.forEach(s->getListDto.add(FaqResponseDto.GetFaqDTO(s)));
        return getListDto;

    }

    //faq 등록
    public Boolean create(String email,FaqRequestDto.CreateDTO request) {
        User user = findByEmail(email);
        //400 - 데이터 미입력
        if(request.getTitle().isEmpty() || request.getContents().isEmpty() )
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        try{
            Faq faq = Faq.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .build();
            faqRepository.save(faq);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }


    }


    //faq 삭제
    public Boolean delete(String email,FaqRequestDto.DeleteDTO request) {
        //404 - id없음
        Faq faq = findByFaqId(request.getFaqId());
        try{
            faqRepository.deleteById(request.getFaqId());
            return true;
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    //faq 수정
    public Boolean update(String email,FaqRequestDto.UpdateDTO request) {
        //400 - 데이터 미입력
        if(request.getFaqId() == null || request.getTitle().isEmpty() || request.getContents().isEmpty()
        )
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        User user = findByEmail(email);
        try{
            Optional<Faq> modifyFaq = faqRepository.findByFaqId(request.getFaqId());
            Faq modifiedFaq = modifyFaq.get();
            modifiedFaq.setTitle(request.getTitle());
            modifiedFaq.setContents(request.getContents());
            faqRepository.save(modifiedFaq);
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    private Faq findByFaqId(Long faqId) {
        return faqRepository.findById(faqId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_ID));
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
