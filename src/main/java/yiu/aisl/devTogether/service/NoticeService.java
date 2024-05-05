package yiu.aisl.devTogether.service;

import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Notice;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.dto.NoticeRequestDto;
import yiu.aisl.devTogether.dto.NoticeResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.NoticeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private Logger log;

    //공지사항 전체조회
    public List<NoticeResponseDto> getList() throws  Exception{
        List<Notice> notice = noticeRepository.findByOrderByCreatedAtDesc();
        List<NoticeResponseDto>  getListDTO= new ArrayList<>();
        notice.forEach(s->getListDTO.add(NoticeResponseDto.GetNoticeDTO(s)));
        return getListDTO;
    }

    //공지사항 등록
    public Boolean create(NoticeRequestDto.CreateDTO request) {
        //400 데이터 미입력
        if(request.getTitle() == null || request.getContents() == null || request.getCategory() == null)
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        try {

            NoticeCategory category = NoticeCategory.fromValue(request.getCategory());
            Notice notice = Notice.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .file(request.getFile())
                    .category(category)
                    .build();

            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;

    }
    //공지사항 삭제
    public Boolean delete(NoticeRequestDto.DeleteDTO request) {

        // 404 - id 없음
        Notice notice = findBynNoticeId(request.getNoticeId());
        try{
            //deleteByNoticeId로 하면 SQL에서 NoticeId 프로퍼티를 인식하지못함
            noticeRepository.deleteById(request.getNoticeId());
            return true;
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }




    // 공지사항 수정
    public Boolean update(NoticeRequestDto.UpdateDTO request) {
        // 400 데이터 미입력
        if (request.getTitle() == null || request.getContents() == null || request.getCategory() == null
                || request.getNoticeId() == null|| request.getFile() == null ) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        Optional<Notice> modifyNotice = noticeRepository.findByNoticeId(request.getNoticeId());
        if (modifyNotice.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        try {

            NoticeCategory category = NoticeCategory.fromValue(request.getCategory());
            Notice modifiedNotice = modifyNotice.get();
            modifiedNotice.setTitle(request.getTitle());
            modifiedNotice.setContents(request.getContents());
            modifiedNotice.setCategory(category);
            modifiedNotice.setFile(request.getFile());


            noticeRepository.save(modifiedNotice);
            return true;
        }    catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


    private Notice findBynNoticeId(Long noticeId) {
        return noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
