package yiu.aisl.devTogether.service;

import yiu.aisl.devTogether.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.*;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.*;
import yiu.aisl.devTogether.config.CustomUserDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ScrapRepository scrapRepository;
    private final CommentRepository commentRepository;
    private final MatchingRepository matchingRepository;
    private final UserProfileRepository userProfileRepository;


    // [API]  내 정보 조회
    public Object getMyProfile(CustomUserDetails userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));
        return MyProfileResponseDto.builder()
                .email(user.get().getEmail())
                .name(user.get().getName())
                .nickname(user.get().getNickname())
                .role(user.get().getRole().ordinal()) //
                .gender(user.get().getGender().ordinal()) //
                .age(user.get().getAge())
                .location1(user.get().getLocation1())
                .location2(user.get().getLocation2())
                .location3(user.get().getLocation3())
                .subject1(user.get().getSubject1())
                .subject2(user.get().getSubject2())
                .subject3(user.get().getSubject3())
                .subject4(user.get().getSubject4())
                .subject5(user.get().getSubject5())
                .method(user.get().getMethod())
                .fee(user.get().getFee())
                .build();
    }

    // [API] 내 정보 수정
    public Boolean updateProfile(CustomUserDetails userDetails, MyProfileRequestDto dto) {
        if(userRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()) {
            new CustomException(ErrorCode.NOT_EXIST_ID); // 해당 사용자 없음 (404)
        }

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(()->
                new CustomException(ErrorCode.NO_AUTH)); // 권한 오류 (403)

        // 데이터 미입력 (400)
        if (dto.getEmail().isEmpty() || dto.getName().isEmpty() || dto.getNickname().isEmpty() || dto.getRole() == null || dto.getGender() == null || dto.getAge() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        // 데이터 중복(닉네임) (409)
        if(dto.getNickname() != user.getNickname()) {
            if(userRepository.findByEmail(dto.getNickname()).isPresent()) {
                throw  new CustomException(ErrorCode.DUPLICATE);
            }
        }

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setRole(RoleCategory.values()[dto.getRole()]);
        user.setGender(GenderCategory.values()[dto.getGender()]);
        user.setAge(dto.getAge());
        user.setLocation1(dto.getLocation1());
        user.setLocation2(dto.getLocation2());
        user.setLocation3(dto.getLocation3());
        user.setSubject1(dto.getSubject1());
        user.setSubject2(dto.getSubject2());
        user.setSubject3(dto.getSubject3());
        user.setSubject4(dto.getSubject4());
        user.setSubject5(dto.getSubject5());
        user.setMethod(dto.getMethod());
        user.setFee(dto.getFee());
        return true;
    }

    // [API] 내가 작성한 댓글 조회
    public Object getMyComment(CustomUserDetails userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));

        List<Comment> myComments = commentRepository.findByUser(user.get());
        return myComments.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내가 작성한 글 조회
    public Object getMyBoard(CustomUserDetails userDetails) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        ));

        List<Board> myBoards = boardRepository.findByUser(user.get());
        return myBoards.stream()
                .map(BoardDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내가 스크랩한 글 조회
//    public Object getMyScrap(CustomUserDetails userDetails) {
//        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
//                () -> new CustomException(ErrorCode.NO_AUTH)
//        ));
//
//        List<Scrap> myScraps = scrapRepository.findByUser(user.get());
//        return myScraps.stream()
//                .map(ScrapDto::new)
//                .collect(Collectors.toList());
//    }
//
    // [API] 내 멘티 관리하기
    public Object getMyMentee(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        );

        List<Matching> myMentee = matchingRepository.findByMentor(userProfile);
        return myMentee.stream()
                .map(MatchingResponseDto::new)
                .collect(Collectors.toList());
    }

    // [API] 내 멘토 관리하기
    public Object getMyMentor(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(
                () -> new CustomException(ErrorCode.NO_AUTH)
        );

        List<Matching> myMentor = matchingRepository.findByMentee(userProfile);
        return myMentor.stream()
                .map(MatchingResponseDto::new)
                .collect(Collectors.toList());
    }
//
//    // [API] 내 멘토 프로필 변경하기
//    public Boolean changeProfile(CustomUserDetails userDetails) {
//        Optional<User> user = userRepository.findByEmail(userDetails.getUser().getEmail());
//
//        return true;
//    }
}
