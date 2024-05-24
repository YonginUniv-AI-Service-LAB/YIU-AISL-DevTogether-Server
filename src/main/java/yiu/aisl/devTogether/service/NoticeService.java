package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Notice;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.NoticeRequestDto;
import yiu.aisl.devTogether.dto.NoticeResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.NoticeRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private  final UserRepository userRepository;


    //공지사항 전체조회
    public List<NoticeResponseDto> getList() throws  Exception{
        List<Notice> notice = noticeRepository.findByOrderByCreatedAtDesc();   //내림차순으로 정렬한걸 Notice객체의 리스트 형태를 notice로 하겠다
        List<NoticeResponseDto>  getListDTO= new ArrayList<>();
        notice.forEach(s->getListDTO.add(NoticeResponseDto.GetNoticeDTO(s)));
        return getListDTO;
    }

    //공지사항 상세조회
    public NoticeResponseDto getDetail(NoticeRequestDto.DetailDTO request)throws Exception {

        // 404 - id 없음
        Notice notice = findByNoticeId(request.getNoticeId());

        try{
            NoticeResponseDto response = NoticeResponseDto.GetNoticeDTO(notice);
            return response;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }


    }



    //공지사항 등록
    public Boolean create(String email, NoticeRequestDto.CreateDTO request) {
        NoticeCategory noticeCategory = NoticeCategory.fromInt(request.getNoticeCategory());    //열거형 상수
        RoleCategory role = RoleCategory.fromInt(request.getRole());
        // 400 - 데이터 미입력
        if (request.getTitle().isEmpty()|| request.getContents().isEmpty()
                || request.getNoticeCategory() == null || request.getRole() == null)
        {
            System.out.println("test1");
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 403 - 권한 없음
       /* if (role != RoleCategory.MANAGER) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }*/
        User user = findByEmail(email);
        if(user.getRole() !=RoleCategory.관리자 ){
            throw  new CustomException(ErrorCode.NO_AUTH);
        }



        try {
            Notice notice = Notice.builder()
                    .role(role)
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .noticeCategory(noticeCategory)
                    .file(request.getFile())
                    .build();
            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return true;
    }


    //공지사항 삭제
    public Boolean delete(String email, NoticeRequestDto.DeleteDTO request) {
        RoleCategory role = RoleCategory.fromInt(request.getRole());
        // 404 - id 없음
        Notice notice = findByNoticeId(request.getNoticeId());
        if(notice == null){
            throw new CustomException(ErrorCode.NOT_EXIST_ID);
        }
        // 403 - 권한 없음

        User user = findByEmail(email);
        if(user.getRole() !=RoleCategory.관리자 ){
            throw  new CustomException(ErrorCode.NO_AUTH);
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
    public Boolean update(String email, NoticeRequestDto.UpdateDTO request) {
        NoticeCategory noticeCategory = NoticeCategory.fromInt(request.getNoticeCategory());
        RoleCategory role = RoleCategory.fromInt(request.getRole());
        // 400 - 데이터 미입력
        if (request.getTitle().isEmpty()|| request.getContents().isEmpty()
                || request.getNoticeCategory() == null || request.getRole() == null
                || request.getNoticeId() == null|| request.getFile() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 403 - 권한 없음
        User user = findByEmail(email);
        if(user.getRole() !=RoleCategory.관리자 ){
            throw  new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            Optional<Notice> modifyNotice = noticeRepository.findByNoticeId(request.getNoticeId());
            Notice modifiedNotice = modifyNotice.get();
            modifiedNotice.setRole(role);
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

    private Notice findByNoticeId(Long noticeId) {
        return noticeRepository.findByNoticeId(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
