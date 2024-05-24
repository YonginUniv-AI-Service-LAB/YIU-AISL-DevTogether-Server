package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.MatchingRepository;
import yiu.aisl.devTogether.repository.ScrapRepository;
import yiu.aisl.devTogether.repository.UserProfileRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {


    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final UserProfileRepository userProfileRepository;
    private final ScrapRepository scrapRepository;

    //멘토 조회
    public List<User> mentorList(String email) {
        User user = findByEmail(email);

        //현재 멘티면 멘토 리스트 보여줌
        if (user.getRole() == RoleCategory.멘티) {
            return userRepository.findByRole(RoleCategory.멘토);
        }
        //현재 멘토멘티면 멘토 리스트 보여줌
        if (user.getRole() == RoleCategory.멘토멘티) {
            return userRepository.findByRole(RoleCategory.멘토);
        }
        return null;
    }

    //멘티 조회
    public List<User> menteeList(String email) {
        User user = findByEmail(email);
        //현재 멘토면 멘티 리스트 보여줌
        if (user.getRole() == RoleCategory.멘토) {
            return userRepository.findByRole(RoleCategory.멘티);
        }
        //현재 멘토멘티면 멘티 리스트 보여줌
        if (user.getRole() == RoleCategory.멘토멘티) {
            return userRepository.findByRole(RoleCategory.멘티);
        }
        return null;
    }


    // 멘토 스크랩( 현재 멘티일 때)
    public Boolean mentorScrap(String email, MatchingRequestDto.ScrapDto request) throws Exception{
        User user = findByEmail(email);

        //400 데이터 미입력
        if(request.getScrapId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음
        Integer userProfile  = findByUserProfileId(request.getScrapId()).getRole();
        System.out.println("dsfsdfsdfsdfsdfsdf"+  userProfile);

        try{
            Scrap scrap = Scrap.builder()
                    .type(1)          //유저프로필 스크랩
                    .typeId(request.getScrapId())
                    .user(user)
                    .build();
            scrapRepository.save(scrap);
            return true;


        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }




    }


    // 멘티 스크랩( 현재 멘토일 때)
    public Boolean menteeScrap(String email, MatchingRequestDto.ScrapDto request) {
        User user = findByEmail(email);
        return true;
    }


    //신청하기
    public Boolean apply(String email, MatchingRequestDto.ApplyDTO request) {
        User user = findByEmail(email);

        // 멘토면 멘티한테 신청
        if (user.getRole() == RoleCategory.멘토) {


        }

        //멘티면 멘토한테 신청
        if (user.getRole() == RoleCategory.멘티) {

        }

        return true;
    }


    //신청 수락
    public Boolean approve(String email, MatchingRequestDto.ApproveDTO request) {
        return true;
    }

    //신청 삭제
    public Boolean delete(String email, MatchingRequestDto.DeleteDTO request) {
        return true;

    }

    //신청 거절
    public Boolean refusal(String email, MatchingRequestDto.RefusalDTO request) {


        return true;

    }

    //신청 확정
    public Boolean confirm(String email, MatchingRequestDto.ConfirmDTO request) {
        return true;
    }


    //신청 종료
    public Boolean end(String email, MatchingRequestDto.EndDTO request) {
        User user = findByEmail(email);
        if (user.getRole() == RoleCategory.멘토) {
            Matching matching = findByMatchingId(request.getMatchingId());
            matching.setStatus(StatusCategory.완료);
            matchingRepository.save(matching);
        }
        return true;
    }


    private Matching findByMatchingId(Long matchingId) {
        return matchingRepository.findByMatchingId(matchingId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public UserProfile findByUserIdAndRole(User userId, Integer role) {
        return userProfileRepository.findByUserIdAndRole(userId, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
    public UserProfile findByUserProfileId(Long userProfileId) {
        return userProfileRepository.findByUserProfileId( userProfileId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }


}
