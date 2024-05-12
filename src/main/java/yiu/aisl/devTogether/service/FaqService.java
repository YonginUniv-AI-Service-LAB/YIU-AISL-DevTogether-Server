package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Faq;

import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.FaqRequestDto;
import yiu.aisl.devTogether.dto.FaqResponsetDto;

import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.FaqRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FaqService {
    private final FaqRepository faqRepository;

    //faq 조회
    public List <FaqResponsetDto> getList() throws Exception{
        List<Faq> faq = faqRepository.findByOrderByCreatedAtDesc();  //생성 일자를 기준으로 내림차순으로 정렬
        List<FaqResponsetDto> getListDto = new ArrayList<>();
        faq.forEach(s->getListDto.add(FaqResponsetDto.GetFaqDTO(s)));
        return getListDto;

    }

    //faq 등록
    public Boolean create(FaqRequestDto.CreateDTO request) {

        RoleCategory role = RoleCategory.fromInt(request.getRole());
        //400 - 데이터 미입력
        if(request.getTitle() == null || request.getContents() == null || request.getRole() == null)
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        //403 - 권한 없음
        if(role != RoleCategory.MANAGER){
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try{
            Faq faq = Faq.builder()
                    .role(role)
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .build();
            faqRepository.save(faq);
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;

    }


    //faq 삭제
    public Boolean delete(FaqRequestDto.DeleteDTO request) {
        RoleCategory role = RoleCategory.fromInt(request.getRole());

        //404 - id없음
        Faq faq = findByFaqId(request.getFaqId());
        if(faq == null){
            throw  new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        //403 - 권한 없음
        if(role != RoleCategory.MANAGER){
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try{
            faqRepository.deleteById(request.getFaqId());
            return true;
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    //faq 수정
    public Boolean update(FaqRequestDto.UpdateDTO request) {

        RoleCategory role = RoleCategory.fromInt(request.getRole());

        //400 - 데이터 미입력
        if(request.getTitle() == null || request.getContents() == null
                || request.getRole() == null|| request.getFaqId() == null)
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);

        //403 - 권한 없음
        if(role != RoleCategory.MANAGER){
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try{
            Optional<Faq> modifyFaq = faqRepository.findByFaqId(request.getFaqId());
            Faq modifiedFaq = modifyFaq.get();
            modifiedFaq.setRole(role);
            modifiedFaq.setTitle(request.getTitle());
            modifiedFaq.setContents(request.getContents());
            faqRepository.save(modifiedFaq);
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;

    }

    private Faq findByFaqId(Long faqId) {
        return faqRepository.findById(faqId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
