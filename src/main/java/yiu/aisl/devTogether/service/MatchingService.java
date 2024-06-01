package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.MatchingCategory;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
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
    public Object menteeList(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();

        List<UserProfile> userProfiles = userProfileRepository.findByRole(2);

        return userProfiles.stream()
                .map(ProfileResponseDto::new)
                .collect(Collectors.toList());
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




    // 신청하기
    public Boolean apply(String email, MatchingRequestDto.MenteeApplyDTO request) throws Exception {
        User user = findByEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Integer profileRole = userProfile.getRole();
        try {

            if (profileRole == 1) { //멘토
                UserProfile mentee = findByUserProfileId(request.getMentee().getUserProfileId());
                Matching matching = Matching.builder()
                        .status("신청")
                        .mentor(userProfile)
                        .mentee(mentee)
                        .build();
                matchingRepository.save(matching);
            } else if (profileRole == 2) { //멘티
                UserProfile mentor = findByUserProfileId(request.getMentor().getUserProfileId());
                Matching  matching = Matching.builder()
                        .status("신청")
                        .mentee(userProfile)
                        .mentor(mentor)
                        .build();
                matchingRepository.save(matching);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }






    //신청 수락
    public Boolean approve( String email, MatchingRequestDto.ApproveDTO request) throws Exception{
        User user = findByEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Matching matching = findByMatchingId(request.getMatchingId());
        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 403 - 권한 없음
        if (!matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId()) &&
                !matching.getMentee().getUserProfileId().equals(userProfile.getUserProfileId())) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        try {
            if(matching.getStatus().equals("신청")){
                matching.setStatus("성사됨");
            }

            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }    //자기랑 관련된 matchingId가 아닌 경우 예외처리 해야함

    //신청 삭제
    public Boolean delete( String email, MatchingRequestDto.DeleteDTO request) throws Exception{
        User user = findByEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Matching matching = findByMatchingId(request.getMatchingId());
        // 400: 데이터 미입력
        if(request.getMatchingId() == null ){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 403 - 권한 없음
        if (!matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId()) &&
                !matching.getMentee().getUserProfileId().equals(userProfile.getUserProfileId())) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        try {
            if(matching.getStatus().equals("신청")){
                matchingRepository.deleteById(request.getMatchingId());
                matching.setStatus("성사안됨");
            }

            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 거절
    public Boolean refusal(String email, MatchingRequestDto.RefusalDTO request)throws Exception {
        User user = findByEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Matching matching = findByMatchingId(request.getMatchingId());

        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 403 - 권한 없음
        if (!matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId()) &&
                !matching.getMentee().getUserProfileId().equals(userProfile.getUserProfileId())) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            if(matching.getStatus().equals("신청")){
                matching.setStatus("거절");
            }
            return true;


        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 확정
    public Boolean confirm(String email,MatchingRequestDto.ConfirmDTO request)throws Exception {
        User user = findByEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Matching matching = findByMatchingId(request.getMatchingId());
        // 400: 데이터 미입력
        if(request.getMatchingId() == null){
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 403 - 권한 없음
        if (!matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId()) &&
                !matching.getMentee().getUserProfileId().equals(userProfile.getUserProfileId())) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            if(matching.getStatus().equals("성사됨") ){
                matching.setStatus("진행");


            }

            return true;
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 종료
    public Boolean end(String email,MatchingRequestDto.EndDTO request) throws Exception {
        User user = findByEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Matching matching = findByMatchingId(request.getMatchingId());
        Integer profileRole = userProfile.getRole();

        // 403 - 권한 없음
        if (!matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId()) &&
                !matching.getMentee().getUserProfileId().equals(userProfile.getUserProfileId())) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        //403 - 권한 없음
        if(profileRole.equals(2)){
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        try {
                if(matching.getStatus().equals("진행") ){
                     matching.setStatus("완료");;
                     matchingRepository.save(matching);
                }

            return true;
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    public UserProfile findByUserProfileId(Long userProfileId) {
        return userProfileRepository.findByUserProfileId( userProfileId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }



    private UserProfile findByUserProfile(User user) {
        return userProfileRepository.findByUser(user)
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


}
