package yiu.aisl.devTogether.service;

import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.QuestionCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.*;
import yiu.aisl.devTogether.config.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardScrapRepository boardScrapRepository;
    private final MatchingScrapRepository matchingScrapRepository;
    private final CommentRepository commentRepository;
    private final MatchingRepository matchingRepository;
    private final UserProfileRepository userProfileRepository;
    private final PushRepository pushRepository;
    private final FilesService filesService;

    // [API]  내 정보 조회
    public Object getMyProfile(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_AUTH));

        return MyProfileResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .gender(user.getGender().name())
                .age(user.getAge())
                .location1(user.getLocation1())
                .location2(user.getLocation2())
                .location3(user.getLocation3())
                .question(user.getQuestion().getValue())
                .answer(user.getAnswer())
                .build();
    }

    // [API] 내 정보 수정
    public Boolean updateProfile(CustomUserDetails userDetails, MyProfileRequestDto dto) throws Exception {
        if(userRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()) {
            new CustomException(ErrorCode.NOT_EXIST_ID); // 해당 사용자 없음 (404)
        }

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(()->
                new CustomException(ErrorCode.NO_AUTH)); // 권한 오류 (403)
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_AUTH));


        // 데이터 미입력 (400)
        if (dto.getEmail().isEmpty() || dto.getName().isEmpty() || dto.getNickname().isEmpty() || dto.getRole() == null || dto.getGender() == null || dto.getAge() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setRole(RoleCategory.values()[dto.getRole()]);
        user.setGender(GenderCategory.values()[dto.getGender()]);
        user.setAge(dto.getAge());
        user.setLocation1(dto.getLocation1());
        user.setLocation2(dto.getLocation2());
        user.setLocation3(dto.getLocation3());
        user.setAnswer(dto.getAnswer());
        user.setQuestion(QuestionCategory.fromInt(dto.getQuestion()));

        return true;
    }

    // [API] 내가 댓글을 달았던 글 조회
    public Object getMyComment(CustomUserDetails userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));
        Optional<UserProfile> userProfile = Optional.ofNullable(userProfileRepository.findByUser(userDetails.getUser()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));
        List<Comment> myComments = commentRepository.findByUserProfile(userProfile.get());
        return myComments.stream()
                .map(CommentRequestDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내가 작성한 글 조회
    public Object getMyBoard(CustomUserDetails userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));
        Optional<UserProfile> userProfile = Optional.ofNullable(userProfileRepository.findByUser(userDetails.getUser()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));
        List<Board> myBoards = boardRepository.findByUserProfile(userProfile.get());
        return myBoards.stream()
                .map(MyBoardDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내가 스크랩한 게시글 조회
    public Object getMyScrap(CustomUserDetails userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));

        List<BoardScrap> myBoardScrap = boardScrapRepository.findByUser(user.get());
        return myBoardScrap.stream()
                .map(BoardScrapDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내가 스크랩 멘토 프로필 조회
    public Object getMentorProfileScrap(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        System.out.println(user);

        List<MatchingScrap> myProfileScraps = matchingScrapRepository.findByUser(user);
        return myProfileScraps.stream()
                .filter(myProfileScrap -> myProfileScrap.getStatus() == 1)
                .map(ProfileScrapDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내가 스크랩 멘티 프로필 조회
    public Object getMenteeProfileScrap(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        System.out.println(user);

        List<MatchingScrap> myProfileScraps = matchingScrapRepository.findByUser(user);
        return myProfileScraps.stream()
                .filter(myProfileScrap -> myProfileScrap.getStatus() == 2)
                .map(ProfileScrapDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내 멘티 관리하기
    public Object getMyMentee(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();
        Optional<UserProfile> userProfile = userProfileRepository.findByUserIdAndRole(user, 1);

        List<Matching> myMentee = matchingRepository.findByMentor(userProfile);
        return myMentee.stream()
                .map(MatchingResponseDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내 멘토 관리하기
    public Object getMyMentor(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();
        Optional<UserProfile> userProfile = userProfileRepository.findByUserIdAndRole(user, 2);

        List<Matching> myMentor = matchingRepository.findByMentee(userProfile);
        return myMentor.stream()
                .map(MatchingResponseDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내 멘토 프로필 조회
    public Object getMyMentorProfile(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();

        Optional<UserProfile> userProfile = userProfileRepository.findByUserIdAndRole(user, 1);
        return userProfile.stream()
                .map(UserProfileResponseDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내 멘티 프로필 조회
    public Object getMyMenteeProfile(CustomUserDetails userDetails) {
        Long user = userDetails.getUser().getId();

        Optional<UserProfile> userProfile = userProfileRepository.findByUserIdAndRole(user, 2);
        return userProfile.stream()
                .map(UserProfileResponseDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내 멘토 프로필 변경하기
    public Boolean changeMentorProfile(CustomUserDetails userDetails, UserProfileRequestDto dto, MultipartFile img) throws Exception {
        Long user = userDetails.getUser().getId();
        UserProfile userProfile = userProfileRepository.findByUserIdAndRole(user, 1).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH) // 권한 오류 (403)
        );

        if(dto.getIntroduction().isEmpty() || dto.getPr().isEmpty() || dto.getPortfolio().isEmpty() ||
                dto.getContents().isEmpty() || dto.getSchedule().isEmpty() || dto.getMethod().isEmpty() || dto.getFee()==null) {
            userProfile.setChecks(0);
        } else {
            userProfile.setChecks(1);
        }
        Boolean imgs = filesService.isFile(img);
        userProfile.setIntroduction(dto.getIntroduction());
        userProfile.setPr(dto.getPr());
        userProfile.setPortfolio(dto.getPortfolio());
        userProfile.setContents(dto.getContents());
        userProfile.setSchedule(dto.getSchedule());
        userProfile.setMethod(dto.getMethod());
        userProfile.setFee(dto.getFee());
        userProfile.setSubject1(dto.getSubject1());
        userProfile.setSubject2(dto.getSubject2());
        userProfile.setSubject3(dto.getSubject3());
        userProfile.setSubject4(dto.getSubject4());
        userProfile.setSubject5(dto.getSubject5());
        userProfile.setImg(imgs);
        userProfile.setUpdatedAt(LocalDateTime.now());
        userProfileRepository.save(userProfile);
        if (imgs) {
            filesService.saveFileDb(img, 1, userProfile.getUserProfileId());
        }
        return true;
    }

    // [API] 내 멘티 프로필 변경하기
    public Boolean changeMenteeProfile(CustomUserDetails userDetails, UserProfileRequestDto dto, MultipartFile img) throws Exception {
        Long user = userDetails.getUser().getId();
        UserProfile userProfile = userProfileRepository.findByUserIdAndRole(user, 2).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH) // 권한 오류 (403)
        );

        if(dto.getIntroduction().isEmpty() || dto.getPr().isEmpty() || dto.getPortfolio().isEmpty() ||
        dto.getContents().isEmpty() || dto.getSchedule().isEmpty() || dto.getMethod().isEmpty() || dto.getFee()==null) {
            userProfile.setChecks(0);
        } else {
            userProfile.setChecks(1);
        }
        Boolean imgs = filesService.isFile(img);
        userProfile.setIntroduction(dto.getIntroduction());
        userProfile.setPr(dto.getPr());
        userProfile.setPortfolio(dto.getPortfolio());
        userProfile.setContents(dto.getContents());
        userProfile.setSchedule(dto.getSchedule());
        userProfile.setMethod(dto.getMethod());
        userProfile.setFee(dto.getFee());
        userProfile.setSubject1(dto.getSubject1());
        userProfile.setSubject2(dto.getSubject2());
        userProfile.setSubject3(dto.getSubject3());
        userProfile.setSubject4(dto.getSubject4());
        userProfile.setSubject5(dto.getSubject5());
        userProfile.setImg(imgs);
        userProfile.setUpdatedAt(LocalDateTime.now());
        userProfileRepository.save(userProfile);
        if (imgs) {
            filesService.saveFileDb(img, 1, userProfile.getUserProfileId());
        }
        return true;
    }

    // [API] 알림 확인
    public Boolean checkAlarm(CustomUserDetails userDetails, Long pushId) {
        if(userRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()) {
            new CustomException(ErrorCode.NOT_EXIST_ID); // 해당 사용자 없음 (404)
        }

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(()->
                new CustomException(ErrorCode.NO_AUTH)); // 권한 오류 (403)

        Push push = pushRepository.getReferenceById(pushId);
        push.setChecks(0);
        return true;
    }

    // [API] 알림 내역 조회
    public Object getMyAlarms(CustomUserDetails userDetails) {
        if(userRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()) {
            new CustomException(ErrorCode.NOT_EXIST_ID); // 해당 사용자 없음 (404)
        }

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(() ->
                new CustomException(ErrorCode.NO_AUTH)); // 권한 오류 (403)

        user.setChecks(0);
        List<Push> myPush = pushRepository.findByUser(user);
        return myPush.stream()
                .map(PushResponseDto::new)
                .collect(Collectors.toList());
    }
}
