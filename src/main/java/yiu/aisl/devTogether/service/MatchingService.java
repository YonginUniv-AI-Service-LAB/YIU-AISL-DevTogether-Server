package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.PushCategory;
import yiu.aisl.devTogether.domain.state.SubjectCategory;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.dto.ProfileResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {


    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;
    private final UserProfileRepository userProfileRepository;
    private final MatchingScrapRepository matchingScrapRepository;
    private final PushRepository pushRepository;


    //멘토 조회(멘티가 멘토 조회)
    public Object mentorList(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();
        List<UserProfile> userProfiles = userProfileRepository.findByRole(1);
        return userProfiles.stream()
                .filter(userProfile -> userProfile.getChecks() != null && userProfile.getChecks() == 1)
                .map(userProfile -> {
                    // 스크랩 여부를 확인하여 1 또는 0으로 설정
                    Integer scrap = matchingScrapRepository.findByUserAndUserProfileAndStatus(userDetails.getUser(), userProfile, 1).isPresent() ? 1 : 0;
                    return new ProfileResponseDto(userProfile, userProfile.getUser(), scrap);
                })
                .collect(Collectors.toList());
    }

    //멘티 조회(멘토가 멘티 조회)
    public Object menteeList(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();
        List<UserProfile> userProfiles = userProfileRepository.findByRole(2);

        return userProfiles.stream()
                .filter(userProfile -> userProfile.getChecks() != null && userProfile.getChecks() == 1)
                .map(userProfile -> {
                    // 스크랩 여부를 확인하여 1 또는 0으로 설정
                    Integer scrap = matchingScrapRepository.findByUserAndUserProfileAndStatus(userDetails.getUser(), userProfile, 2).isPresent() ? 1 : 0;
                    return new ProfileResponseDto(userProfile, userProfile.getUser(), scrap);
                })
                .collect(Collectors.toList());
    }

    // 멘토 스크랩( 현재 멘티일 때)
    public Boolean mentorScrap(String email, MatchingRequestDto.ScrapDto request) throws Exception {
        User user = findByEmail(email);

        //400 데이터 미입력
        if (request.getScrapId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404: id 없음
        UserProfile userProfile = findByUserProfileId(request.getScrapId());

        if (matchingScrapRepository.findByUserAndUserProfileAndStatus(user, userProfile, 1).isPresent()) {
            matchingScrapRepository.deleteByUserAndUserProfileAndStatus(user, userProfile, 1);
            return true;
        } else {
            try {
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
    public Boolean menteeScrap(String email, MatchingRequestDto.ScrapDto request) throws Exception {
        User user = findByEmail(email);

        //400 데이터 미입력
        if (request.getScrapId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        // 404: id 없음
        UserProfile userProfile = findByUserProfileId(request.getScrapId());

        if (matchingScrapRepository.findByUserAndUserProfileAndStatus(user, userProfile, 2).isPresent()) {
            matchingScrapRepository.deleteByUserAndUserProfileAndStatus(user, userProfile, 2);
            return true;
        } else {
            try {
                MatchingScrap menteeScrap = MatchingScrap.builder()
                        .status(2)
                        .user(user)
                        .userProfile(userProfile)
                        .createdAt(LocalDateTime.now())
                        .build();
                matchingScrapRepository.save(menteeScrap);
                return true;
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }


    // 신청하기(멘티가 멘토에게)
    public Boolean applyMentor(CustomUserDetails userDetails, MatchingRequestDto.MentorApplyDTO request) throws Exception {
        Long userId = userDetails.getUser().getId();

        UserProfile userProfile = findByUserIdAndRole(userId, 2);
        try {

            UserProfile mentor = findByUserProfileId(request.getMentor().getUserProfileId());

            if (mentor.getRole() != 1) { // 대상이 멘티가 아닌 경우
                throw new CustomException(ErrorCode.NOT_EXIST_MEMBER);
            }

            List<SubjectCategory> mentorSubjects = Arrays.asList(
                    mentor.getSubject1(), mentor.getSubject2(), mentor.getSubject3(),
                    mentor.getSubject4(), mentor.getSubject5()
            );
            System.out.println("멘토" + mentorSubjects);
            if (request.getContents().isEmpty() || request.getTutoringFee() == null) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }

            List<SubjectCategory> menteeSubjects = new ArrayList<>();
            if (request.getSubject1() != null) menteeSubjects.add(SubjectCategory.fromInt(request.getSubject1()));
            if (request.getSubject2() != null) menteeSubjects.add(SubjectCategory.fromInt(request.getSubject2()));
            if (request.getSubject3() != null) menteeSubjects.add(SubjectCategory.fromInt(request.getSubject3()));
            if (request.getSubject4() != null) menteeSubjects.add(SubjectCategory.fromInt(request.getSubject4()));
            if (request.getSubject5() != null) menteeSubjects.add(SubjectCategory.fromInt(request.getSubject5()));

            if (menteeSubjects.isEmpty()) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }

            boolean matchingSubject = mentorSubjects.stream().anyMatch(menteeSubjects::contains);
            System.out.println(menteeSubjects);

            if (!matchingSubject) {
                throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
            }

            Matching matching = Matching.builder()
                    .status("신청")
                    .mentee(userProfile)
                    .mentor(mentor)
                    .subject1(request.getSubject1() != null ? SubjectCategory.fromInt(request.getSubject1()) : SubjectCategory.fromInt(0))
                    .subject2(request.getSubject2() != null ? SubjectCategory.fromInt(request.getSubject2()) : SubjectCategory.fromInt(0))
                    .subject3(request.getSubject3() != null ? SubjectCategory.fromInt(request.getSubject3()) : SubjectCategory.fromInt(0))
                    .subject4(request.getSubject4() != null ? SubjectCategory.fromInt(request.getSubject4()) : SubjectCategory.fromInt(0))
                    .subject5(request.getSubject5() != null ? SubjectCategory.fromInt(request.getSubject5()) : SubjectCategory.fromInt(0))

                    .tutoringFee(request.getTutoringFee())
                    .contents(request.getContents())
                    .build();
            matchingRepository.save(matching);

            Push push = Push.builder()
                    .type(PushCategory.매칭)
                    .contents("과외를 신청했습니다.")
                    .user(mentor.getUser())
                    .typeId(matching.getMatchingId())
                    .checks(0)
                    .build();
            pushRepository.save(push);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    // 신청하기(멘토가 멘티에게)
    public Boolean applyMentee(CustomUserDetails userDetails, MatchingRequestDto.MenteeApplyDTO request) throws Exception {
        Long userId = userDetails.getUser().getId();
        UserProfile userProfile = findByUserIdAndRole(userId, 1);
        try {


            UserProfile mentee = findByUserProfileId(request.getMentee().getUserProfileId());
            if (mentee.getRole() != 2) { // 대상이 멘티가 아닌 경우
                throw new CustomException(ErrorCode.NOT_EXIST_MEMBER);
            }

            List<SubjectCategory> menteeSubjects = Arrays.asList(
                    mentee.getSubject1(), mentee.getSubject2(), mentee.getSubject3(),
                    mentee.getSubject4(), mentee.getSubject5()
            );


            System.out.println("멘티" + menteeSubjects);
            if (request.getContents().isEmpty() || request.getTutoringFee() == null) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }
            List<SubjectCategory> mentorSubjects = new ArrayList<>();
            if (request.getSubject1() != null) mentorSubjects.add(SubjectCategory.fromInt(request.getSubject1()));
            if (request.getSubject2() != null) mentorSubjects.add(SubjectCategory.fromInt(request.getSubject2()));
            if (request.getSubject3() != null) mentorSubjects.add(SubjectCategory.fromInt(request.getSubject3()));
            if (request.getSubject4() != null) mentorSubjects.add(SubjectCategory.fromInt(request.getSubject4()));
            if (request.getSubject5() != null) mentorSubjects.add(SubjectCategory.fromInt(request.getSubject5()));

            if (mentorSubjects.isEmpty()) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }
            boolean matchingSubject = menteeSubjects.stream().anyMatch(mentorSubjects::contains);
            System.out.println("멘토" + mentorSubjects);

            if (!matchingSubject) {
                throw new CustomException(ErrorCode.USER_DATA_INCONSISTENCY);
            }

            Matching matching = Matching.builder()
                    .status("신청")
                    .mentor(userProfile)
                    .mentee(mentee)
                    .subject1(request.getSubject1() != null ? SubjectCategory.fromInt(request.getSubject1()) : SubjectCategory.NULL)
                    .subject2(request.getSubject2() != null ? SubjectCategory.fromInt(request.getSubject2()) : SubjectCategory.NULL)
                    .subject3(request.getSubject3() != null ? SubjectCategory.fromInt(request.getSubject3()) : SubjectCategory.NULL)
                    .subject4(request.getSubject4() != null ? SubjectCategory.fromInt(request.getSubject4()) : SubjectCategory.NULL)
                    .subject5(request.getSubject5() != null ? SubjectCategory.fromInt(request.getSubject5()) : SubjectCategory.NULL)
                    .tutoringFee(request.getTutoringFee())
                    .contents(request.getContents())
                    .build();
            matchingRepository.save(matching);
            Push push = Push.builder()
                    .type(PushCategory.매칭)
                    .contents("과외를 신청했습니다.")
                    .user(mentee.getUser())
                    .typeId(matching.getMatchingId())
                    .checks(0)
                    .build();
            pushRepository.save(push);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    // 신청 수락
    public Boolean approve(CustomUserDetails userDetails, MatchingRequestDto.ApproveDTO request) throws Exception {
        Long userId = userDetails.getUser().getId();
        Matching matching = findByMatchingId(request.getMatchingId());

        UserProfile mentorProfile = matching.getMentor();
        UserProfile menteeProfile = matching.getMentee();

        // 400: 데이터 미입력
        if (request.getMatchingId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 매칭 ID가 사용자와 관련 있는지 확인
        boolean isRelatedToUser = mentorProfile.getUser().getId().equals(userId) || menteeProfile.getUser().getId().equals(userId);
        if (!isRelatedToUser) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        try {
            if ("신청".equals(matching.getStatus())) {
                matching.setStatus("성사됨");
                UserProfile recipientProfile;
                if (mentorProfile.getUser().getId().equals(userId)) {
                    recipientProfile = menteeProfile;
                } else {
                    recipientProfile = mentorProfile;
                }
                String nickname = userDetails.getUsername();
                Push push = Push.builder()
                        .type(PushCategory.매칭)
                        .contents("과외를 수락했습니다.")
                        .user(recipientProfile.getUser())
                        .typeId(matching.getMatchingId())
                        .checks(0)
                        .build();
                pushRepository.save(push);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 거절
    public Boolean refusal(CustomUserDetails userDetails,  MatchingRequestDto.RefusalDTO request) throws Exception {
        Long userId = userDetails.getUser().getId();
        Matching matching = findByMatchingId(request.getMatchingId());

        UserProfile mentorProfile = matching.getMentor();
        UserProfile menteeProfile = matching.getMentee();

        // 400: 데이터 미입력
        if (request.getMatchingId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 매칭 ID가 사용자와 관련 있는지 확인
        boolean isRelatedToUser = mentorProfile.getUser().getId().equals(userId) || menteeProfile.getUser().getId().equals(userId);
        if (!isRelatedToUser) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            if (matching.getStatus().equals("신청")) {
                matching.setStatus("거절");
                UserProfile recipientProfile;
                if (mentorProfile.getUser().getId().equals(userId)) {
                    recipientProfile = menteeProfile;
                } else {
                    recipientProfile = mentorProfile;
                }

                String nickname = userDetails.getUsername();
                Push push = Push.builder()
                        .type(PushCategory.매칭)
                        .contents("과외를 거절했습니다.")
                        .user(recipientProfile.getUser())
                        .typeId(matching.getMatchingId())
                        .checks(0)
                        .build();
                pushRepository.save(push);
            }
            return true;


        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신청 삭제
    public Boolean delete(CustomUserDetails userDetails, MatchingRequestDto.DeleteDTO request) throws Exception {
        Long userId = userDetails.getUser().getId();
        Matching matching = findByMatchingId(request.getMatchingId());

        UserProfile mentorProfile = matching.getMentor();
        UserProfile menteeProfile = matching.getMentee();

        // 400: 데이터 미입력
        if (request.getMatchingId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 매칭 ID가 사용자와 관련 있는지 확인
        boolean isRelatedToUser = mentorProfile.getUser().getId().equals(userId) || menteeProfile.getUser().getId().equals(userId);
        if (!isRelatedToUser) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            if (matching.getStatus().equals("신청")) {
                matchingRepository.deleteById(request.getMatchingId());
                matching.setStatus("성사안됨");
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 확정
    public Boolean confirm(CustomUserDetails userDetails, MatchingRequestDto.ConfirmDTO request) throws Exception {
        Long userId = userDetails.getUser().getId();
        Matching matching = findByMatchingId(request.getMatchingId());

        UserProfile mentorProfile = matching.getMentor();
        UserProfile menteeProfile = matching.getMentee();

        // 400: 데이터 미입력
        if (request.getMatchingId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 매칭 ID가 사용자와 관련 있는지 확인
        boolean isRelatedToUser = mentorProfile.getUser().getId().equals(userId) || menteeProfile.getUser().getId().equals(userId);
        if (!isRelatedToUser) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }

        try {
            if (matching.getStatus().equals("성사됨")) {
                matching.setStatus("진행");
                UserProfile recipientProfile;
                if (mentorProfile.getUser().getId().equals(userId)) {
                    recipientProfile = menteeProfile;
                } else {
                    recipientProfile = mentorProfile;
                }

                String nickname = userDetails.getUsername();
                Push push = Push.builder()
                        .type(PushCategory.매칭)
                        .contents("과외가 확정되었습니다.")
                        .user(recipientProfile.getUser())
                        .typeId(matching.getMatchingId())
                        .checks(0)
                        .build();
                pushRepository.save(push);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //신청 종료
    public Boolean end(String email, MatchingRequestDto.EndDTO request) throws Exception {
        User user = findByEmail(email);
        UserProfile userProfile = findByUserAndRole(user, 1);
        Matching matching = findByMatchingId(request.getMatchingId());
        Integer profileRole = userProfile.getRole();


        // 매칭 ID가 자기랑 관련 있는지 확인
        boolean isRelatedToUser = matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId()) ||
                matching.getMentee().getUserProfileId().equals(userProfile.getUserProfileId());
        // 403 - 권한 없음
        if (!isRelatedToUser) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        //403 - 권한 없음
        if (profileRole.equals(2)) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            if (matching.getStatus().equals("진행")) {
                matching.setStatus("완료");
                UserProfile recipientProfile;
                if (matching.getMentor().getUserProfileId().equals(userProfile.getUserProfileId())) {
                    recipientProfile = matching.getMentee();
                } else {
                    recipientProfile = matching.getMentor();
                }

                Push push = Push.builder()
                        .type(PushCategory.매칭)
                        .contents("과외를 수락했습니다.")
                        .user(recipientProfile.getUser())
                        .typeId(matching.getMatchingId())
                        .checks(0)
                        .build();
                pushRepository.save(push);
            }
            return true;
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }




    public UserProfile findByUserProfileId(Long userProfileId) {
        return userProfileRepository.findByUserProfileId(userProfileId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    private Matching findByMatchingId(Long matchingId) {
        return matchingRepository.findByMatchingId(matchingId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private UserProfile findByUserIdAndRole(Long userId, Integer role) {
        return userProfileRepository.findByUserIdAndRole(userId, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private UserProfile findByUserAndRole(User user, Integer role) {
        return userProfileRepository.findByUserAndRole(user, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

}