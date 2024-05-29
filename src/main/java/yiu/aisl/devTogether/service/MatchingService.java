package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.MatchingCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.*;

import java.time.LocalDateTime;
import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {


    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final UserProfileRepository userProfileRepository;
    private final MatchingScrapRepository matchingScrapRepository;

    //멘토 조회
    public List<UserInformation> mentorList(String email) {
        User user = findByEmail(email);
        //현재 멘티, 멘토멘티면 멘토 리스트 보여줌
        if (user.getRole() == RoleCategory.멘티 || user.getRole() == RoleCategory.멘토멘티) {
            return userRepository.findByRole(RoleCategory.멘토);    //멘토 정보 일부만 보여줌
        }
        return null;
    }

    //멘티 조회
    public List<UserInformation> menteeList(String email) {
        User user = findByEmail(email);
        //현재 멘토, 멘토멘티면 멘티 리스트 보여줌
        if (user.getRole() == RoleCategory.멘토 || user.getRole() == RoleCategory.멘토멘티) {
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
        UserProfile userProfile  = findByUserProfileId(request.getScrapId());

            try{
                MatchingScrap mentorScrap = MatchingScrap.builder()
                        .status(1)
                        .user(user)
                        .userProfileId(userProfile)
                        .createdAt(LocalDateTime.now())
                        .build();
                matchingScrapRepository.save(mentorScrap);
                return true;
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
    }


    // 멘티 스크랩( 현재 멘토일 때)
    public Boolean menteeScrap(String email, MatchingRequestDto.ScrapDto request) throws Exception{
        User user = findByEmail(email);

        //400 데이터 미입력
        if(request.getScrapId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 404: id 없음
        UserProfile userProfile  = findByUserProfileId(request.getScrapId());
        try{
            MatchingScrap menteeScrap = MatchingScrap.builder()
                    .status(2)
                    .user(user)
                    .userProfileId(userProfile)
                    .createdAt(LocalDateTime.now())
                    .build();
            matchingScrapRepository.save(menteeScrap);
            return true;
        }catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //멘티가 신청하기
    public Boolean menteeApply(String email, MatchingRequestDto.MenteeApplyDTO request) throws Exception {

        User user = findByEmail(email);

        MatchingCategory matchingCategory = MatchingCategory.fromInt(request.getMatchingCategory());

        // 403 권한 없음
        if (user.getRole() == RoleCategory.멘토) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        // 404 데이터미입력
        if (request.getMatchingCategory() == null || request.getMentor() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            Matching matching = Matching.builder()
                    .matchingCategory(matchingCategory)
                    .status(StatusCategory.신청)
                  //  .mentor()
                    .build();
            matchingRepository.save(matching);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    public UserProfile findByUserProfileId(Long userProfileId) {
        return userProfileRepository.findByUserProfileId( userProfileId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Matching findByMatchingId(Long matchingId) {
        return matchingRepository.findByMatchingId(matchingId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public UserProfile findByUserIdAndRole(User userId, RoleCategory role) {
        return userProfileRepository.findByUserIdAndRole(userId, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    //멘토가 신청하기
    public Boolean mentorApply(String email, MatchingRequestDto.MentorApplyDTO request) throws Exception{
        User user = findByEmail(email);
        try{
            return true;
        }catch ( Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 수락
    public Boolean approve(String email, MatchingRequestDto.ApproveDTO request) throws Exception{
        User user = findByEmail(email);

        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
           // Mathcing mathcing = Matching.builder()
                //    .build()
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 삭제
    public Boolean delete(String email, MatchingRequestDto.DeleteDTO request) throws Exception{
        User user = findByEmail(email);
        Matching matching = findByMatchingId(request.getMatchingId());
        // 400: 데이터 미입력
        if(request.getMatchingId() == null || matching.getStatus() !=StatusCategory.진행){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            matchingRepository.deleteById(request.getMatchingId());
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 거절
    public Boolean refusal(String email, MatchingRequestDto.RefusalDTO request)throws Exception {

        User user = findByEmail(email);
        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 확정
    public Boolean confirm(String email, MatchingRequestDto.ConfirmDTO request)throws Exception {
        User user = findByEmail(email);
        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 종료
    public Boolean end(String email, MatchingRequestDto.EndDTO request) throws Exception {
        try {
            User user = findByEmail(email);

            if (user.getRole() == RoleCategory.멘토) {
                Matching matching = findByMatchingId(request.getMatchingId());
                matching.setStatus(StatusCategory.완료);
                matchingRepository.save(matching);
            }
            return true;
        } catch (CustomException e) {
           throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
