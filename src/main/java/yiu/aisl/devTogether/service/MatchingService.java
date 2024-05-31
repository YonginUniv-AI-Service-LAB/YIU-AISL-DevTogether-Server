package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.MatchingCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.dto.MatchingResponseDto;
import yiu.aisl.devTogether.dto.ProfileResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {


    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final UserProfileRepository userProfileRepository;
    private final MatchingScrapRepository matchingScrapRepository;

    //멘토 조회(멘티가 멘토 조회)
    public Object mentorList(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();

        List<UserProfile> userProfiles = userProfileRepository.findByRole(1);

        return userProfiles.stream()
                .map(ProfileResponseDto::new)
                .collect(Collectors.toList());
    }

    //멘티 조회(멘토가 멘티 조회)
    public List<UserProfile> menteeList(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();

        // 403 권한 오류
        UserProfile userProfile = userProfileRepository.findByUserIdAndRole(user, 2).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        );
        //현재 멘토면 멘티 리스트 보여줌
       if (userProfile.getRole().equals(RoleCategory.멘토) ) {
            return userProfileRepository.findUserProfileByRole(RoleCategory.멘티);
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

        if(matchingScrapRepository.findByUserAndUserProfileAndStatus(user, userProfile, 1).isPresent()) {
            matchingScrapRepository.deleteByUserAndUserProfileAndStatus(user, userProfile,1);
            return true;
        } else {
            try{
                MatchingScrap mentorScrap = MatchingScrap.builder()
                        .status(1)
                        .user(user)
                        .userProfile(userProfile)
                        .createdAt(LocalDateTime.now())
                        .build();
                matchingScrapRepository.save(mentorScrap);
                return true;
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
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

        if(matchingScrapRepository.findByUserAndUserProfileAndStatus(user, userProfile, 2).isPresent()) {
            matchingScrapRepository.deleteByUserAndUserProfileAndStatus(user, userProfile, 2);
            return true;
        } else {
            try{
                MatchingScrap menteeScrap = MatchingScrap.builder()
                        .status(2)
                        .user(user)
                        .userProfile(userProfile)
                        .createdAt(LocalDateTime.now())
                        .build();
                matchingScrapRepository.save(menteeScrap);
                return true;
            }catch (Exception e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }


    //멘티가 신청하기
  /*  public Boolean menteeApply(String email, MatchingRequestDto.MenteeApplyDTO request) throws Exception {

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

    }*/
    // 신청하기
    public Boolean apply(CustomUserDetails userDetails, MatchingRequestDto.MentorApplyDTO request) throws Exception  {
        Long user = userDetails.getUser().getId();
       // MatchingCategory matchingCategory = MatchingCategory.fromInt(request.getMatchingCategory());

        UserProfile userProfileMentor = userProfileRepository.findByUserIdAndRole(user, 2).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        );
       /* UserProfile userProfileMentee = userProfileRepository.findByUserIdAndRole(user, RoleCategory.멘티).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        );*/

        // 404 데이터미입력
        if ( request.getMentee() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            Matching matching = Matching.builder()
                    //.matchingCategory(matchingCategory)
                    .status(StatusCategory.신청)

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

    public UserProfile findByUserIdAndRole(Long userId, Integer role) {
        return userProfileRepository.findByUserIdAndRole(userId, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }




    //신청 수락
    public Boolean approve( CustomUserDetails userDetails,  MatchingRequestDto.ApproveDTO request) throws Exception{
        User user = userDetails.getUser();
        Matching matching = findByMatchingId(request.getMatchingId());
        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            if(matching.getStatus().equals(StatusCategory.신청)){
                matching.setStatus(StatusCategory.성사됨);
            }

            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 삭제
    public Boolean delete( CustomUserDetails userDetails, MatchingRequestDto.DeleteDTO request) throws Exception{
        User user = userDetails.getUser();
        Matching matching = findByMatchingId(request.getMatchingId());
        // 400: 데이터 미입력
        if(request.getMatchingId() == null ){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            if(matching.getStatus().equals(StatusCategory.신청)){
                matchingRepository.deleteById(request.getMatchingId());
            }
            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 거절
    public Boolean refusal(CustomUserDetails userDetails, MatchingRequestDto.RefusalDTO request)throws Exception {

        User user = userDetails.getUser();

        Matching matching = findByMatchingId(request.getMatchingId());


        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            if(matching.getStatus().equals(StatusCategory.신청)){
                matching.setStatus(StatusCategory.거절);
                matching.setStatus(StatusCategory.성사안됨);

            }
            return true;


        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 확정
    public Boolean confirm(CustomUserDetails userDetails,MatchingRequestDto.ConfirmDTO request)throws Exception {
        User user = userDetails.getUser();

        Matching matching = findByMatchingId(request.getMatchingId());

        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        try {
            if(matching.getStatus().equals(StatusCategory.신청) &&matching.getStatus().equals(StatusCategory.성사됨) ){
                matching.setStatus(StatusCategory.진행);


            }

            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 종료
    public Boolean end(CustomUserDetails userDetails,MatchingRequestDto.EndDTO request) throws Exception {
        Long user = userDetails.getUser().getId();
        Matching matching = findByMatchingId(request.getMatchingId());

        //403 권한 없음
        UserProfile userProfile = userProfileRepository.findByUserIdAndRole(user, 2).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        );
        try {


            if (userProfile.getRole().equals(RoleCategory.멘토) && matching.equals(StatusCategory.진행)) {

                matching.setStatus(StatusCategory.완료);
                matchingRepository.save(matching);
            }
            return true;
        } catch (CustomException e) {
           throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
