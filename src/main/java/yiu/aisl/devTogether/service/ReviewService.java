package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Matching;
import yiu.aisl.devTogether.domain.Review;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.ReviewRequestDto;
import yiu.aisl.devTogether.dto.ReviewResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.MatchingRepository;
import yiu.aisl.devTogether.repository.ReviewRepository;
import yiu.aisl.devTogether.repository.UserProfileRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    public final UserRepository userRepository;
    public final ReviewRepository reviewRepository;
    public final UserProfileRepository userProfileRepository;
    public final MatchingRepository matchingRepository;

//    //보낸 리뷰 조회 -- 모든 리뷰 보이게? 내 룰과 리뷰 카테고리가 같은거 가져오기
//    public List<ReviewResponseDto> getSend(String email, Integer role) throws Exception {
//
//        //403: 권한없음 -- 로그인 됬는지 확인
//        User user = findByUserEmail(email);
//        UserProfile userProfile = findByUserProfileId(user, role);
//        List<Matching> matchingList = matchingRepository.findByMentor(userProfile);
////        List<Review> reviewList =reviewRepository.findByMatchingIdAndCategory(matching, role);
//        List<Review> reviewList = new ArrayList<>();
//        for (Matching matching : matchingList) {
//            List<Review> reviews = reviewRepository.findByMatchingIdAndCategory(matching, role);
//            reviewList.addAll(reviews);
//        }
//        try {
//            // Review를 ReviewResponseDto로 변환
//            return reviewList.stream()
//                    .map(ReviewResponseDto::new)
//                    .collect(Collectors.toList());
////            return null;
//        } catch (Exception e) {
//            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    //받은 리뷰 조회 --- 숨져긴 리뷰 안 보이게 설정? 내 룰과 리뷰 카테고리가 다른걸 가져오기 ---test 필요
//    public List<ReviewResponseDto> getReceive(String email, Integer role) throws Exception {
//        Integer roles = role;
//        if (roles == 1) {
//            roles = 2;
//        } else if (roles == 2) {
//            roles = 1;
//        }
//        //403: 권한없음 -- 로그인 됬는지 확인
//        User user = findByUserEmail(email);
//        UserProfile userProfile = findByUserProfileId(user, roles);
//        List<Matching> matchingList = matchingRepository.findByMentor(userProfile);
//        List<Review> reviewList = new ArrayList<>();
//        for (Matching matching : matchingList) {
//            List<Review> reviews = reviewRepository.findByMatchingIdAndCategory(matching, role);
//            reviewList.addAll(reviews);
//        }
//        try {
//            // Review를 ReviewResponseDto로 변환
//            return reviewList.stream()
//                    .map(ReviewResponseDto::new)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }

    //리뷰 작성 -- test 미실시
    public Boolean creatreview(String email, ReviewRequestDto.creatDto request) throws Exception {
        //400: 데이터 미입력
        if (request.category == null || request.contents == null || request.matchingId == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //404 : 매칭 아이디 없음
        Matching matching = findByMachingId(request.matchingId);

        //403: 권한없음 -- 맨토 맨티 확인? , 매칭 후 작성 시간이 지나가 버림
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endAt = matchingRepository.findByMatchingId(request.matchingId).get().getEndedAt();

        User user = findByUserEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        Integer profileRole = userProfile.getRole();

        // N일 지나기 전 true
        if (ChronoUnit.DAYS.between(endAt, now) <= 7) {
            try {
                Review review = Review.builder()
                        .matchingId(matching)
                        .contents(request.getContents())
                        .hide(false)
                        .category(profileRole)
                        .build();
                reviewRepository.save(review);
                return true;
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
    }

    //리뷰 숨기기 -- test 미실시
    public Boolean switchHide(String email, ReviewRequestDto.hideDto request) throws Exception {
        //404: 리뷰 id 없음
        findByReviewId(request.review_id);
        Optional<Review> review = reviewRepository.findByReviewId(request.review_id);
        Review getReview = review.get();
        //403: 권한없음 --- 리뷰와 리뷰 프로필 값 다름
        User user = findByUserEmail(email);
        UserProfile userProfile = findByUserProfile(user);
        if (!getReview.getCategory().equals(userProfile.getRole())) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            getReview.setHide(request.getHide());
            reviewRepository.save(getReview);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public Matching findByMachingId(Long id) {
        return matchingRepository.findByMatchingId(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    public UserProfile findByUserProfileId(User user, Integer id) {
        return userProfileRepository.findByUserAndRole(user, id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public UserProfile findByUserProfile(User user) {
        return userProfileRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public Review findByReviewId(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ID));
    }

    //멘토값
//    public List<Review> findUserProfileByMatchingId(String email) {
//        User user = findByUserEmail(email);
//        UserProfile userProfile = findByUserProfile(user);
//        List<Matching> matching = matchingRepository.findByMentor(user);
//        List<Review> reviewList =reviewRepository.findByMatchingId(matching)
//        return reviewRepository.findByMatchingId(matching);
//    }

}
