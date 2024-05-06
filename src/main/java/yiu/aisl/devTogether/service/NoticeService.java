package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Notice;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
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


    //공지사항 전체조회
    public List<NoticeResponseDto> getList() throws  Exception{
        List<Notice> notice = noticeRepository.findByOrderByCreatedAtDesc();
        List<NoticeResponseDto>  getListDTO= new ArrayList<>();
        notice.forEach(s->getListDTO.add(NoticeResponseDto.GetNoticeDTO(s)));
        return getListDTO;
    }


    //공지사항 등록
    public Boolean create(NoticeRequestDto.CreateDTO request) {
        NoticeCategory noticeCategory = NoticeCategory.fromInt(request.getNoticeCategory());    //열거형 상수
        RoleCategory roleCategory = RoleCategory.fromInt(request.getRoleCategory());
        // 400 - 데이터 미입력
        if (request.getTitle() == null || request.getContents() == null
                || request.getNoticeCategory() == null || request.getRoleCategory() == null
                || request.getFile() == null)
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        // 403 - 권한 없음
        if (roleCategory != RoleCategory.MANAGER) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            Notice notice = Notice.builder()
                    .roleCategory(roleCategory)
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .file(request.getFile())
                    .noticeCategory(noticeCategory)
                    .build();
            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }


    //공지사항 삭제
    public Boolean delete(NoticeRequestDto.DeleteDTO request) {
        RoleCategory roleCategory = RoleCategory.fromInt(request.getRoleCategory());
        // 404 - id 없음
        Notice notice = findBynNoticeId(request.getNoticeId());
        if(notice == null){
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        // 403 - 권한 없음
        if(roleCategory != RoleCategory.MANAGER){
            throw new CustomException(ErrorCode.NO_AUTH);
        }
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
        NoticeCategory noticeCategory = NoticeCategory.fromInt(request.getNoticeCategory());
        RoleCategory roleCategory = RoleCategory.fromInt(request.getRoleCategory());
        // 400 - 데이터 미입력
        if (request.getTitle() == null || request.getContents() == null
                || request.getNoticeCategory() == null || request.getRoleCategory() == null
                || request.getNoticeId() == null|| request.getFile() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 403 - 권한 없음
        if (roleCategory != RoleCategory.MANAGER) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            Optional<Notice> modifyNotice = noticeRepository.findByNoticeId(request.getNoticeId());
            Notice modifiedNotice = modifyNotice.get();
            modifiedNotice.setRoleCategory(roleCategory);
            modifiedNotice.setTitle(request.getTitle());
            modifiedNotice.setContents(request.getContents());
            modifiedNotice.setNoticeCategory(noticeCategory);
            modifiedNotice.setFile(request.getFile());
            noticeRepository.save(modifiedNotice);
        }    catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
    private Notice findBynNoticeId(Long noticeId) {
        return noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
